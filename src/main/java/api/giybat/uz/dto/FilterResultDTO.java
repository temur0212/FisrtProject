package api.giybat.uz.dto;

import java.util.List;

public class FilterResultDTO<T> {
    private List<T> data;
    private Long totalElements;

    public FilterResultDTO(List<T> data, Long totalElements) {
        this.data = data;
        this.totalElements = totalElements;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
