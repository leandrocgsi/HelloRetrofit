package br.com.erudio.helloretrofitcrud.data.remote;

import br.com.erudio.helloretrofitcrud.data.model.Post;
import retrofit2.Call;
// import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
// import rx.Observable;

public interface APIService {

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title, @Field("body") String body, @Field("userId") long userId);

    @PUT("/posts/{id}")
    @FormUrlEncoded
    Call<Post> updatePost(@Path("id") long id, @Field("title") String title, @Field("body") String body, @Field("userId") long userId);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") long id);

    /* RxJava
    @POST("/posts")
    @FormUrlEncoded
    Observable<Post> savePost(@Field("title") String title, @Field("body") String body, @Field("userId") long userId);
    */
}