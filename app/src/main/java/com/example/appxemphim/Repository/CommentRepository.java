    package com.example.appxemphim.Repository;

    import android.content.Context;
    import android.util.Log;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.lifecycle.MutableLiveData;

    import com.example.appxemphim.Model.Comment;
    import com.example.appxemphim.Model.Profile;
    import com.example.appxemphim.Network.CommentService;
    import com.example.appxemphim.Network.FavoriteListApiService;
    import com.example.appxemphim.Network.RetrofitInstance;
    import com.example.appxemphim.Request.CommentRequest;
    import com.example.appxemphim.UI.Utils.Resource;
    import com.example.appxemphim.Utilities.FirebaseUtils;
    import com.example.appxemphim.Utilities.SessionManager;
    import com.google.firebase.database.ChildEventListener;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.MutableData;
    import com.google.firebase.database.Transaction;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.atomic.AtomicInteger;

    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class CommentRepository {
        private final CommentService commentService;
        private final DatabaseReference commentsRef;
        private final List<Comment> comments = new ArrayList<>();
        private final MutableLiveData<List<Comment>> commentsLiveData = new MutableLiveData<>();
        private final ProfileRepository profileRepository;

        public CommentRepository(Context context,String movieId) {
            String ma = SessionManager.getToken(context);
            Log.d("favorite", "token:  "+ma);
            this.commentService = RetrofitInstance.createService(CommentService.class,ma);
            commentsRef = FirebaseUtils.getDb()
                    .child("Comment")
                    .child(movieId);
            profileRepository = new ProfileRepository(context);
        }

        public MutableLiveData<List<Comment>> getCommentsLiveData() {
            return commentsLiveData;
        }

        public void start() {
            commentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();
                    List<Comment> tempList = new ArrayList<>();
                    int total = (int) snapshot.getChildrenCount();
                    AtomicInteger counter = new AtomicInteger(0);
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Comment c = child.getValue(Comment.class);
                        c.setCommentId(child.getKey());
                        if (c != null) {
                            c.setCommentId(child.getKey());
                            fetchProfileAndAppend(c, tempList, counter, total);
                        }
                    }
                }

                @Override public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        private void fetchProfileAndAppend(Comment comment, List<Comment> tempList, AtomicInteger counter, int total) {
            MutableLiveData<Resource<Profile>> liveData = new MutableLiveData<>();
            profileRepository.getProfile(comment.getUser_id(), liveData);
            liveData.observeForever(resource -> {
                if (resource != null && resource.getStatus() == Resource.Status.SUCCESS) {
                    Profile profile = resource.getData();
                    if (profile != null) {
                        comment.setAvatar(profile.getAvatar());
                        comment.setName(profile.getName());
                    }
                    tempList.add(comment);
                    if (counter.incrementAndGet() == total) {
                        comments.addAll(tempList);
                        commentsLiveData.setValue(new ArrayList<>(comments));
                        listenChanges();
                    }
                }
            });
        }

        private void fetchProfileAndAppend(Comment comment) {
            MutableLiveData<Resource<Profile>> liveData = new MutableLiveData<>();
            profileRepository.getProfile(comment.getUser_id(), liveData);

            liveData.observeForever(resource -> {
                if (resource != null && resource.getStatus() == Resource.Status.SUCCESS) {
                    Profile profile = resource.getData();
                    if (profile != null) {
                        comment.setAvatar(profile.getAvatar());
                        comment.setName(profile.getName());
                    }
                    comments.add(comment);
                    commentsLiveData.setValue(new ArrayList<>(comments));
                }
            });
        }



        private void listenChanges() {
            commentsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snap, String prevKey) {
                    Comment c = snap.getValue(Comment.class);
                    if (c != null) {
                        c.setCommentId(snap.getKey());
                        boolean exists = false;
                        for (Comment com : comments) {
                            if (com.getCommentId().equals(c.getCommentId())) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            if (c.getParent_comment_id() == null || c.getParent_comment_id().isEmpty()) {
                                fetchProfileAndAppend(c);
                            } else {
                                comments.add(c);
                                commentsLiveData.setValue(new ArrayList<>(comments));
                            }
                        }
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snap, String prevKey) {
                    Comment updatedComment = snap.getValue(Comment.class);
                    if (updatedComment != null) {
                        updatedComment.setCommentId(snap.getKey());
                        for (int i = 0; i < comments.size(); i++) {
                            if (comments.get(i).getCommentId().equals(updatedComment.getCommentId())) {
                                updatedComment.setAvatar(comments.get(i).getAvatar());
                                updatedComment.setName(comments.get(i).getName());
                                updatedComment.setExpanded(comments.get(i).isExpanded());
                                updatedComment.setComments(comments.get(i).getComments());
                                comments.set(i, updatedComment);
                                break;
                            }
                        }
                        commentsLiveData.setValue(new ArrayList<>(comments));
                    }
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snap) {
                    String key = snap.getKey();
                    for (int i = 0; i < comments.size(); i++) {
                        if (comments.get(i).getCommentId().equals(key)) {
                            comments.remove(i);
                            break;
                        }
                    }
                    commentsLiveData.setValue(new ArrayList<>(comments));
                }
                @Override public void onChildMoved(@NonNull DataSnapshot snap, String prev) { }
                @Override public void onCancelled(@NonNull DatabaseError error) { }
            });
        }

        public void setLike(String movieId, String commentId, boolean isLike) {
            DatabaseReference likeRef = FirebaseUtils.getDb()
                    .child("Comment")
                    .child(movieId)
                    .child(commentId)
                    .child("like");

            likeRef.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    Integer currentLikes = currentData.getValue(Integer.class);
                    if (currentLikes == null) currentLikes = 0;
                    if (isLike) {
                        currentData.setValue(currentLikes + 1);
                    } else {
                        currentData.setValue(Math.max(0, currentLikes - 1));
                    }
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    if (committed) {
                        Log.d("NONO", "Like updated successfully");

                    } else {
                        Log.e("NONO", "Like update failed", error != null ? error.toException() : null);
                    }
                }
            });
        }

        public void sendComment(CommentRequest commentRequest,Runnable onSuccess, Runnable onFailure){
            commentService.addComment(commentRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (onSuccess != null) onSuccess.run();
                    } else {
                        if (onFailure != null) onFailure.run();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (onFailure != null) onFailure.run();
                }
            });
        }

    }
