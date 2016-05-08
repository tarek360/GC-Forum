package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.APIResponse;
import io.tarek360.gcforum.domain.model.ChatGroup;
import io.tarek360.gcforum.domain.model.ChatGroupCreationResponse;
import io.tarek360.gcforum.domain.model.SimpleResponse;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.ui.chat.Chat;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tarek on 2/17/16.
 */

public interface ChatClient {

  @POST("chat/group/create") Call<ChatGroupCreationResponse> createChatGroup(
      @Body ChatGroup chatGroup);

  @POST("chat/group/create") Observable<ChatGroupCreationResponse> createChatGroupRx(
      @Body ChatGroup chatGroup);

  @POST("chat/group/update/{group_id}") Observable<SimpleResponse> sendMessageRx(
      @Path("group_id") String group_id, @Query("token") String token, @Body Chat chat);

  @GET("chats") Call<List<ChatGroup>> getChatGroups(@Query("token") String token,
      @Query("page") int page, @Query("per_page") int perPage);

  @GET("chat/group/members/{group_id}") Call<APIResponse<User>> getGroupMembers(
      @Path("group_id") String groupId, @Query("page") int page, @Query("per_page") int perPage,
      @Query("token") String userToken);

  @POST("chat/group/rename/{group_id}") Observable<SimpleResponse> renameGroup(
      @Path("group_id") String group_id, @Query("token") String token, @Body ChatGroup name);
}

