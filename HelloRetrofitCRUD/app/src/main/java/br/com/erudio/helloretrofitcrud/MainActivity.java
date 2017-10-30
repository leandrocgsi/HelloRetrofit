package br.com.erudio.helloretrofitcrud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.erudio.helloretrofitcrud.data.model.Post;
import br.com.erudio.helloretrofitcrud.data.remote.APIService;
import br.com.erudio.helloretrofitcrud.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// import rx.Subscriber;
// import rx.android.schedulers.AndroidSchedulers;
// import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView textViewResponse;

    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        textViewResponse = (TextView) findViewById(R.id.tv_response);

        service = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
                    sendPost(title, body);
                }
            }
        });
    }

    public void showErrorMessage() {
        Toast.makeText(this, R.string.mssg_error_submitting_post, Toast.LENGTH_SHORT).show();
    }

    public void sendPost(String title, String body) {

        // RxJava
        /*service.savePost(title, body, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(Post post) {
                        showResponse(post.toString());
                    }
                });
*/
        service.savePost(title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                showErrorMessage();
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void updatePost(long id, String title, String body) {
        service.updatePost(id, title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post updated." + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                showErrorMessage();
                Log.e(TAG, "Unable to update post.");
            }
        });
    }

   /*
   Example of cancelling a request
   private Call<Post> mCall;
    public sendPost(String title, String body) {
        mCall = service.savePost(title, body, 1);
        mCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                if(call.isCanceled()) {
                    Log.e(TAG, "request was aborted");
                }else {
                    Log.e(TAG, "Unable to submit post to API.");
                }
                showErrorMessage();
            }
        });
    }
    public void cancelRequest() {
        mCall.cancel();
    }*/

    public void showResponse(String response) {
        if(textViewResponse.getVisibility() == View.GONE) {
            textViewResponse.setVisibility(View.VISIBLE);
        }
        textViewResponse.setText(response);
    }

    /*
    SEE: https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845 and https://github.com/chikecodes/tutsplus-retrofit2-part2
    */
}