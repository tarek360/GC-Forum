package io.tarek360.gcforum.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.ui.base.BaseActivity;
import io.tarek360.gcforum.util.Logger;

public class ChatActivity extends BaseActivity {
  private static final String FIREBASE_URL = "https://gcforum.firebaseio.com";

  public static String TAG = "FirebaseUI.chat";
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.sendButton) TextView mSendButton;
  @Bind(R.id.messageEdit) EditText mMessageEdit;
  @Bind(R.id.imageView) ImageView imageView;
  @Bind(R.id.title) TextView title;
  private Firebase mRef;
  private Query mChatRef;
  private RecyclerView mMessages;
  private FirebaseRecyclerAdapter<Chat, ChatHolder> mRecycleViewAdapter;
  private String mUsername;
  private String mUserId;
  private DisplayImageOptions mOptions;

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, ChatActivity.class);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    ButterKnife.bind(this);

    // Make sure we have a mUsername
    setupUserNameUserId();

    initToolBar();

    initChatSystem();
  }

  private void initChatSystem() {
    mRef = new Firebase(FIREBASE_URL).child("chat");
    mChatRef = mRef.limitToLast(50);

    mSendButton.setOnClickListener(v -> sendMessage());

    mMessageEdit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
      if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
        sendMessage();
      }
      return true;
    });

    mMessages = (RecyclerView) findViewById(R.id.messagesList);

    MyCustomLayoutManager manager = new MyCustomLayoutManager(this);
    //manager.setReverseLayout(true);

    mMessages.setHasFixedSize(false);
    mMessages.setLayoutManager(manager);

    mRecycleViewAdapter =
        new FirebaseRecyclerAdapter<Chat, ChatHolder>(Chat.class, R.layout.chat_message_item,
            ChatHolder.class, mChatRef, mMessages) {
          @Override public void populateViewHolder(ChatHolder chatView, Chat chat, int position) {
            chatView.setText(chat.getMessage());
            chatView.setAuthor(chat.getAuthorName());
            Logger.debug(ChatActivity.this, "chat.getMessage() " + chat.getMessage());
            if (chat.getAuthorId().equals(mUserId)) {
              chatView.setIsSender(true);
            } else {
              chatView.setIsSender(false);
            }
            Logger.debug(ChatActivity.this, "position " + position);
            Logger.debug(ChatActivity.this, "getItemCount() " + getItemCount());
          }
        };

    mMessages.setAdapter(mRecycleViewAdapter);
  }

  private void sendMessage() {
    String message = mMessageEdit.getText().toString();
    if (TextUtils.isEmpty(message)) {
      return;
    }

    Chat chat = new Chat(message, mUserId, mUsername);

    mRef.push().setValue(chat, (firebaseError, firebase) -> {
      if (firebaseError != null) {
        Log.e(TAG, firebaseError.toString());
      }
    });
    mMessageEdit.setText("");
  }

  private void initToolBar() {
    initUILOptions();

    title.setText(R.string.drawer_item_chat);

    String bgPath = "drawable://" + R.drawable.ic_account_group;
    ImageLoader.getInstance().displayImage(bgPath, imageView, mOptions);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    //toolbar.setNavigationIcon(
    //    new IconDrawable(this, MaterialIcons.md_arrow_back).colorRes(R.color.md_white_1000)
    //        .actionBarSize());
    //toolbar.setNavigationOnClickListener(v -> {
    //
    //  hideKeyBoard();
    //
    //  onBackPressed();
    //});
  }

  private void setupUserNameUserId() {
    mUsername = mSharedPreferencesHelper.getString(User.FLAG_USER_FULL_NAME);
    mUserId = mSharedPreferencesHelper.getString(User.FLAG_USER_ID);
  }

  private void initUILOptions() {

    mOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
        .considerExifParams(true)
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
        .bitmapConfig(Bitmap.Config.ARGB_8888)
        .showImageOnLoading(R.drawable.ic_account)
        .showImageOnFail(R.drawable.ic_account)
        .showImageForEmptyUri(R.drawable.ic_account)
        .build();
  }

  public static class ChatHolder extends RecyclerView.ViewHolder {
    View mView;

    public ChatHolder(View itemView) {
      super(itemView);
      mView = itemView;
    }

    public void setIsSender(Boolean isSender) {
      FrameLayout left_arrow = (FrameLayout) mView.findViewById(R.id.left_arrow);
      FrameLayout right_arrow = (FrameLayout) mView.findViewById(R.id.right_arrow);
      RelativeLayout messageContainer = (RelativeLayout) mView.findViewById(R.id.message_container);
      LinearLayout message = (LinearLayout) mView.findViewById(R.id.message);

      if (isSender) {
        left_arrow.setVisibility(View.GONE);
        right_arrow.setVisibility(View.VISIBLE);
        message.setBackgroundResource(R.drawable.chat_message_background_right);
        messageContainer.setGravity(Gravity.RIGHT);
      } else {
        left_arrow.setVisibility(View.VISIBLE);
        right_arrow.setVisibility(View.GONE);
        message.setBackgroundResource(R.drawable.chat_message_background_left);
        messageContainer.setGravity(Gravity.LEFT);
      }
    }

    public void setText(String text) {
      TextView field = (TextView) mView.findViewById(R.id.message_text);
      field.setText(text);
    }

    public void setAuthor(String text) {
      TextView field = (TextView) mView.findViewById(R.id.author_name);
      field.setText(text);
    }
  }
}
