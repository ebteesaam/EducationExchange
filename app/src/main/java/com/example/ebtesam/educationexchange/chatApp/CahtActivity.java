package com.example.ebtesam.educationexchange.chatApp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CahtActivity extends AppCompatActivity {

    private final List<Messages> messagesList = new ArrayList<>();
    public String idUserBook;
    public String cui;
    public String chatID;
    public String messageStr;
    ImageLoader imageLoader;
    String nameUserBook;
    String snderName;
    private DatabaseReference dataD;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private android.support.v7.widget.Toolbar chatToolBar;
    private TextView addName;
    private TextView lastSean;
    private CircleImageView addImage;
    private String bookId;
    private String idUser;
    private ImageButton pluse;
    private ImageButton send;
    private EditText message;
    private Map chatAppMap;
    private RecyclerView mMessageList;
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;
    private boolean sent = false;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_caht);
//
//        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//        ///////?
//        mAuth=FirebaseAuth.getInstance();
//        dataD =FirebaseDatabase.getInstance().getReference();
//        chatID = dataD.push().getKey();
//
//
//        String pageToUser =getIntent().getStringExtra("userID");
//
//        cui = mCurrentUser.getUid();
//        bookId = getIntent().getStringExtra("id_book");
//
//
//        final ArrayList<Book> books = new ArrayList<>();
//        final ArrayList<User> users=new ArrayList<>();
//        final ArrayList<Book> arrayOfUsers = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//
//        //////query-1
//        Query query = reference
//                .child(getString(R.string.dbname_material));
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Book book = singleSnapshot.getValue(Book.class);
//
//                    if (book.getId_book().equals(ViewBook.id)) {
//                        books.add(singleSnapshot.getValue(Book.class));
//                        idUser = book.getUser_id().toString();
//                    }
//
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(CahtActivity.this.toString(), "onCancelled: query cancelled.");
//            }
//        } );//END Query-1
//
//        //////Query-2
//        Query query2 = reference
//                .child(getString(R.string.dbname_users));
//
//        query2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    User book = singleSnapshot.getValue(User.class);
//                    User bookuser = singleSnapshot.getValue(User.class);
//                    if (bookuser.getUser_id().equals(idUser)) {
//                        users.add(singleSnapshot.getValue(User.class));
//                        Log.d(CahtActivity.this.toString(), bookuser.getUsername().toString());
//
//                        addName.setText(bookuser.getUsername().toString());
//                        ////////////////////////////////////////////////////////////////////////////////
//                        nameUserBook = bookuser.getUsername().toString();
//                        idUserBook =bookuser.getUser_id().toString();
//
//                        setubi(idUserBook);
//
//                        UnvirsalImageLoader.setImage( bookuser.getProfile_photo(),addImage,null,null);
//                    }
//
//                    if(bookuser.getUser_id().equals(cui)){
//                        User getSnderName = singleSnapshot.getValue(User.class);
//                        snderName = getSnderName.getUsername().toString();
//                    }
//                }
//            }
//            //Cases userBook , usertopage
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(CahtActivity.this.toString(), "onCancelled: query cancelled.");
//            }
//        });//END Query-2
//
//
//
//        chatToolBar=(android.support.v7.widget.Toolbar)findViewById(R.id.chatBar);
//        setSupportActionBar(chatToolBar);
//        ActionBar acBat = getSupportActionBar();
//
//
//        acBat.setDisplayHomeAsUpEnabled(true);
//        acBat.setDisplayShowCustomEnabled(true);
//
//        LayoutInflater inflat = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View actBarView = inflat.inflate(R.layout.custom_single_user,null);
//        acBat.setCustomView(actBarView);
//
//        //asgin the view of the user, image and seen
//        addName=(TextView)findViewById(R.id.uWABnameuser);
//        lastSean=(TextView)findViewById(R.id.last);
//        addImage=(CircleImageView )findViewById(R.id.customBarImage);
//
//        //asgin view of imageButton and message
//        pluse=(ImageButton)findViewById(R.id.pluseButton);
//        send=(ImageButton)findViewById(R.id.sendButton);
//        message=(EditText)findViewById(R.id.messageInput);
//
//        mAdapter = new MessageAdapter(messagesList);
//        mMessageList=(RecyclerView)findViewById(R.id.list_of_message);
//        mLinearLayout = new LinearLayoutManager(this);
//        mMessageList.setHasFixedSize(true);
//        mMessageList.setLayoutManager(mLinearLayout);
//        mMessageList.setAdapter(mAdapter);
//
//
//
//        loadMessages();
//
//
/////////////sending message :
//
//
//////////////////////////
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                sendMessage();
//                sent=true;
//                setSend(sent);
//
//
//            }
//        });
//
//    }
//
//    private void sendMessage() {
//
//        messageStr = message.getText().toString() ;
//
//        Messages mesg = new Messages();
//        mesg.setMessageText(messageStr);
//        messagesList.add(mesg);
//        mAdapter.notifyDataSetChanged();
//
//        //mesg.setMessageText(messageStr);
//        //messagesList.add(mesg);
//
//        if(!TextUtils.isEmpty(messageStr)){
//
//
//            String current_user_ref =  cui + "/" + idUserBook;
//            String chat_user_ref =  idUserBook + "/" + cui;
//
//            dataD=FirebaseDatabase.getInstance().getReference().child("chat").child(chatID);
//
//            Map messageMap = new HashMap();
//            messageMap.put("message", messageStr);
//            messageMap.put("seen", false);
//            messageMap.put("time", ServerValue.TIMESTAMP);
//            messageMap.put("from", snderName);
//
//            Map messageUserMap = new HashMap();
//            messageUserMap.put(current_user_ref , messageMap);
//            messageUserMap.put(chat_user_ref , messageMap);
//
//            message.setText("");
//
//            dataD.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if(databaseError!=null){
//                        Log.d("CHAT_LOG",databaseError.getMessage().toString());
//                    }
//                }
//            });
//
//        }//end if
//
//    }//end send method
//
//    private void loadMessages() {
//        dataD.child("chat").child(chatID).child(cui).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public boolean getSend() {return sent;}
//
//    public void setSend(boolean send) {this.sent = sent;}
//
//    public String getubi() {return idUserBook;}
//
//    public void setubi(String idUserBook) {this.idUserBook = idUserBook;}
//
//
//
//}
}