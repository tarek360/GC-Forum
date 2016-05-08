package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class EventDay {

  @SerializedName("date") @Expose long date;
  @SerializedName("sessions") @Expose List<Session> sessions;

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public List<Session> getSessions() {
    return sessions;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }
}
