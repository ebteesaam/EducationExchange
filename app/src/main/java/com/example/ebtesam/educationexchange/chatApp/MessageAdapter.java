package com.example.ebtesam.educationexchange.chatApp;

/**
 * Created by ebtesam on 21/03/2018 AD.
 */

public class MessageAdapter{
//        extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
//
//    //list to hold the messages
//    private List<Messages> mMessageList;
//
//    private DatabaseReference mUserDatabase;
//    private FirebaseAuth mAuth;
//
//    public MessageAdapter(List<Messages> mMessageList) {
//
//        this.mMessageList = mMessageList;
//
//    }

  //  @Override
//    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.message_single_layout ,parent, false);
//
//        return new MessageViewHolder(v);
//
//    }
//
//    public class MessageViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView messageText;
//        public CircleImageView profileImage;
//        // public TextView displayName;
//        //public ImageView messageImage;
//
//        public MessageViewHolder(View view) {
//            super(view);
//
//            messageText = (TextView) view.findViewById(R.id.messageText);
//            // profileImage = (CircleImageView) view.findViewById(R.id.messageImage);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {
//
//        ///   String currentUserID =mAuth.getCurrentUser().getUid();
//
//        Messages c = mMessageList.get(i);
///*
//        String fromUser=c.getFrom();
//        if(fromUser==currentUserID){
//
//            viewHolder.messageText.setBackgroundColor(Color.WHITE);
//            viewHolder.messageText.setTextColor(Color.BLACK);
//        }else{
//            viewHolder.messageText.setBackgroundResource(R.drawable.message_text_background);
//            viewHolder.messageText.setTextColor(Color.WHITE);
//        }
// */
//
//        viewHolder.messageText.setText(c.getMessageText());
//
//
//        //   mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);
///*
//        mUserDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String name = dataSnapshot.child("name").getValue().toString();
//                String image = dataSnapshot.child("thumb_image").getValue().toString();
//
//                viewHolder.displayName.setText(name);
//
//                Picasso.with(viewHolder.profileImage.getContext()).load(image)
//                        .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//       /* if(message_type.equals("text")) {
//
//            viewHolder.messageText.setText(c.getMessageText());
//            viewHolder.messageImage.setVisibility(View.INVISIBLE);
//
//
//        } else {
//
//            viewHolder.messageText.setVisibility(View.INVISIBLE);
//            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessageText())
//                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
//
//        }
//*/
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMessageList.size();
//    }
//
//}
//
    }