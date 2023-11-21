//package com.example.pupomonth;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {
//
//    private ArrayList<PersonalData> mList = null;
//    private Activity context = null;
//    Bitmap bitmap; // 이미지 불러올 때 사용
//
//
//
//    public UserAdapter(Activity context, ArrayList<PersonalData> list) {
//        this.context = context;
//        this.mList = list;
//    }
//
//    class CustomViewHolder extends RecyclerView.ViewHolder {
//        protected ImageView image;
//
//
//        public CustomViewHolder(View view) {
//            super(view);
//            this.image = (ImageView) view.findViewById(R.id.saleImage);
//        }
//    }
//
//
//    @Override
//    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview, null);
//        CustomViewHolder viewHolder = new CustomViewHolder(view);
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
//
//
//
//        //이미지 받아오는 부분
//        Thread mThread = new Thread(){
//            @Override
//            public void run() {
//                try{
//                    URL url = new URL(mList.get(position).getMember_image());
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//
//                    InputStream is = conn.getInputStream();
//                    bitmap = BitmapFactory.decodeStream(is);
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        mThread.start();
//
//        try{
//            mThread.join();
//            viewholder.image.setImageBitmap(bitmap);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return (null != mList ? mList.size() : 0);
//    }
//
//}