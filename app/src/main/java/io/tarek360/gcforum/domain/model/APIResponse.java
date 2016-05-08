package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by tarek on 2/16/16.
 */
public class APIResponse<T> {

  @SerializedName("total") @Expose int total;
  @SerializedName("per_page") @Expose int perPage;
  @SerializedName("current_page") @Expose int currentPage;
  @SerializedName("last_page") @Expose int lastPage;
  @SerializedName("from") @Expose int from;
  @SerializedName("to") @Expose int to;
  @SerializedName("data") @Expose ArrayList<T> data = new ArrayList<>();

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getPerPage() {
    return perPage;
  }

  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getLastPage() {
    return lastPage;
  }

  public void setLastPage(int lastPage) {
    this.lastPage = lastPage;
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public ArrayList<T> getData() {
    return data;
  }

  public void setData(ArrayList<T> data) {
    this.data = data;
  }
}

