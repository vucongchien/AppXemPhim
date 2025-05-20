package com.example.appxemphim.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Giữ các giá trị filter hiện tại:
 * - query: từ khoá tìm kiếm (title)
 * - genres: danh sách thể loại
 * - years: danh sách năm (Integer)
 * - nations: danh sách quốc gia
 * - minRating: điểm tối thiểu
 */
public class FilterData {
    private String query;
    private List<String> genres;
    private List<String> years;
    private List<String> nations;
    private Double minRating;

    // Constructor
    public FilterData(String query,
                      List<String> genres,
                      List<String> years,
                      List<String> nations,
                      Double minRating) {
        this.query     = query;
        this.genres    = genres;
        this.years     = years;
        this.nations   = nations;
        this.minRating = minRating;
    }

    // Getters
    public String getQuery() {
        return query;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getYears() {
        return (years);
    }

    public List<String> getNations() {
        return nations;
    }

    public Double getMinRating() {
        return minRating;
    }

    // Setters (nếu cần thay đổi sau khi khởi tạo)
    public void setQuery(String query) {
        this.query = query;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public void setNations(List<String> nations) {
        this.nations = nations;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }
    private List<Integer> convertYears(List<String> yearStrings) {
        List<Integer> years = new ArrayList<>();
        for (String s : yearStrings) {
            try {
                years.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        return years;
    }
}
