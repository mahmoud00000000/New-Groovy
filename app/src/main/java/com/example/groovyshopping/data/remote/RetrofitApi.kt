package com.example.groovyshopping.data.remote



import com.example.groovyshopping.data.models.MakeOrderResponse
import com.example.groovyshopping.data.models.MorePagesDataModel
import com.example.groovyshopping.data.models.PageDataModel
import com.example.groovyshopping.data.models.ProductDetailsResponse
import com.example.groovyshopping.user.data.models.AddressResponse
import com.example.groovyshopping.user.data.models.AllStoreResponse
import com.example.groovyshopping.user.data.models.CancellationResponse
import com.example.groovyshopping.user.data.models.CartDetailsResponse
import com.example.groovyshopping.user.data.models.CountriesResponse
import com.example.groovyshopping.user.data.models.HomeResponse
import com.example.groovyshopping.user.data.models.MoreDataModel
import com.example.groovyshopping.user.data.models.MoreResponse
import com.example.groovyshopping.user.data.models.NotificationResponse
import com.example.groovyshopping.user.data.models.OfferProductsResponse
import com.example.groovyshopping.user.data.models.OrderDetailsResponse
import com.example.groovyshopping.user.data.models.OrdersListResponse
import com.example.groovyshopping.user.data.models.PaymentMethodsResponse
import com.example.groovyshopping.user.data.models.ProfileResponse
import com.example.groovyshopping.user.data.models.StoreDetailsResponse
import com.example.groovyshopping.user.data.models.StoreResponse
import com.example.groovyshopping.user.data.models.WalletResponse
import com.example.groovyshopping.user.data.remote.DefaultDataModel
import com.example.groovyshopping.user.data.remote.DefaultPayment
import com.google.gson.JsonObject
//import com.homecookapp.user.screens.hyperPayment.HyperPayCredential
//import com.homecookapp.user.screens.hyperPayment.HyperPayCredential.getPaymentTokenCredential
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface RetrofitApi {

    @GET("settings/mobile")
    fun getSetting(): Deferred<Response<DefaultDataModel>>

    @GET("pages?with_notifications_count=1")
    fun getPages(): Deferred<Response<MorePagesDataModel>>

    @GET("pages/{id}")
    fun getPage(@Path("id") id: Int): Deferred<Response<PageDataModel>>

    @GET("transactions?with_wallet=1")
    fun getTransaction(): Deferred<Response<WalletResponse>>

    @POST("transactions")
    fun chargeWallet(@Body data: JsonObject): Deferred<Response<DefaultDataModel>>

    @POST("charge-codes/redeem")
    fun chargeCode(@Body data: JsonObject): Deferred<Response<DefaultDataModel>>

    @GET("payment-methods?return_all=1")
    fun getPaymentMethods(@Query("list_type") list_type: String): Deferred<Response<PaymentMethodsResponse>>

    @GET("countries?return_all=1")
    fun getCountries(): Deferred<Response<CountriesResponse>>

    @POST("auth/login")
    fun login(@Body loginData: JsonObject): Deferred<Response<ProfileResponse>>

    @GET("home/user")
    fun getHome(): Deferred<Response<HomeResponse>>

    @GET
    fun getMore(@Url url: String): Deferred<Response<MoreResponse>>

    @GET("stores")
    fun getSores(
        @Query("category_id") categoryId: Int?,
        @Query("subcategory_id") subcategoryId: Int?
    ): Deferred<Response<MoreResponse>>

    @GET("stores?return_all=1")
    fun getSoresByCategory(
        @Query("category_id") categoryId: Int?,
    ): Deferred<Response<MoreDataModel>>

    @GET("categories?return_all=1")
    fun getAllCategories(): Deferred<Response<MoreDataModel>>

    @GET("stores/{id}")
    fun getStoreDetails(
        @Path("id") id: Int?,
        @Query("with_products") witProducts: Int?
    ): Deferred<Response<StoreResponse>>

    @GET("users/profile")
    fun getProfile(): Deferred<Response<ProfileResponse>>

    @PUT("users/update-profile")
    fun upDateProfile(
        @Body data: JsonObject
    ): Deferred<Response<ProfileResponse>>

    @POST("users/change-phone")
    fun upDatePhone(
        @Body data: JsonObject
    ): Deferred<Response<DefaultDataModel>>

    @POST("users/verify-phone")
    fun verifyNewPhone(
        @Body data: JsonObject
    ): Deferred<Response<ProfileResponse>>

    @POST("settings/delete-my-account")
    fun deleteAccount(): Deferred<Response<DefaultDataModel>>

    @POST("stores")
    fun getStores(
        @Query("active") active: Int?,
        @Query("category_id") categoryId: Int?
    ): Deferred<Response<AllStoreResponse>>

    @POST("stores/{id}")
    fun getStoreDetails(@Path("id") id: Int): Deferred<Response<StoreDetailsResponse>>

    @POST("verifications/confirm")
    fun verifyPhone(@Body loginData: JsonObject): Deferred<Response<ProfileResponse>>


    @POST("addresses/check-stores")
    fun checkAddressStore(): Deferred<Response<DefaultDataModel>>

    @GET("addresses?return_all=1")
    fun getAddress(): Deferred<Response<AddressResponse>>

    @DELETE("addresses/{id}")
    fun deleteAddress(@Path("id") id: Int): Deferred<Response<DefaultDataModel>>

    @Multipart
    @POST("addresses")
    fun addAddress(
        @Part("title") title: RequestBody? = null,
        @Part("address") address: RequestBody? = null,
        @Part("description") description: RequestBody? = null,
        @Part("country_id") country_id: RequestBody? = null,
        @Part("lat") lat: RequestBody? = null,
        @Part("lng") lng: RequestBody? = null,
        @Part("building_number") building_number: RequestBody? = null,
        @Part("floor") floor: RequestBody? = null,
        @Part("apartment_number") apartment_number: RequestBody? = null,
    ): Deferred<Response<DefaultDataModel>>

    @Multipart
    @POST("addresses/{id}")
    fun editAddress(
        @Path("id") id: String,
        @Part("title") title: RequestBody? = null,
        @Part("address") address: RequestBody? = null,
        @Part("description") description: RequestBody? = null,
        @Part("country_id") country_id: RequestBody? = null,
        @Part("lat") lat: RequestBody? = null,
        @Part("lng") lng: RequestBody? = null,
        @Part("_method") _method: RequestBody? = null
    ): Deferred<Response<DefaultDataModel>>

    @GET("products/{id}")
    fun getProductDetails(@Path("id") id: Int): Deferred<Response<ProductDetailsResponse>>

    @POST("users/favourites/add")
    fun addFav(
        @Query("model") model: String,
        @Query("model_id") modelId: Int
    ): Deferred<Response<DefaultDataModel>>

    @POST("users/favourites/remove")
    fun removeFav(
        @Query("model") model: String,
        @Query("model_id") modelId: Int
    ): Deferred<Response<DefaultDataModel>>

    @Multipart
    @POST("carts")
    fun addToCart(
        @PartMap partMap: MutableMap<String, RequestBody>
    ): Deferred<Response<DefaultDataModel>>

    @FormUrlEncoded
    @POST("orders/prepare-order")
    fun prepareOrder(
        @Field("store_id") storeId: Int? = null,
        @Field("code") code: String? = null,
        @Field("receive_from_store") receiveFromStore: Int? = null,
    ): Deferred<Response<CartDetailsResponse>>

    @DELETE("carts/{id}")
    fun deleteItemFromCart(
        @Path("id") itemI: Int
    ): Deferred<Response<DefaultDataModel>>

    @PUT("carts/{id}")
    fun updateItemCountCart(
        @Path("id") itemI: Int,
        @Query("qty") itemCount: Int
    ): Deferred<Response<DefaultDataModel>>

    @FormUrlEncoded
    @POST("orders")
    fun makeOrder(
        @Field("store_id") storeId: Int? = null,
        @Field("code") code: String? = null,
        @Field("address_id") addressId: String? = null,
        @Field("payment_method_id") paymentMethodId: String? = "0",
        @Field("receive_from_store") receiveFromStore: Int? = null,
    ): Deferred<Response<MakeOrderResponse>>

    @GET("orders")
    fun getOrdersList(): Deferred<Response<OrdersListResponse>>

    @GET("orders/{id}")
    fun getOrderDetails(@Path("id") id: Int): Deferred<Response<OrderDetailsResponse>>

    @POST("orders/{id}/reorder")
    fun reOrder(@Path("id") id: Int): Deferred<Response<DefaultDataModel>>

    @POST("orders/{id}/pay-order")
    fun payOrder(@Path("id") id: Int,@Query("payment_method_id")paymentMethodId: Int): Deferred<Response<DefaultDataModel>>

    @POST("orders/{id}/cancel")
    fun cancelOrder(
        @Path("id") id: Int,
        @Body data: JsonObject
    ): Deferred<Response<DefaultDataModel>>

    @GET("cancellation-reasons?return_all=1")
    fun getCancellationReason(): Deferred<Response<CancellationResponse>>

    @GET("users/favourites")
    fun getFavourites(@Query("model") model: String): Deferred<Response<MoreResponse>>

    @DELETE("carts")
    fun clearCart(): Deferred<Response<DefaultDataModel>>

    @GET("notifications")
    fun getNotification(): Deferred<Response<NotificationResponse>>

    @FormUrlEncoded
    @POST("supports")
    fun sendSupportMsg(
        @Field("type") type: String,
        @Field("message") message: String
    ): Deferred<Response<DefaultDataModel>>

    @GET("offers")
    fun getOffers(@Query("page") page: Int): Deferred<Response<OfferProductsResponse>>


    @POST("payments/check-status")
    fun sendPayment(
        @Query("resource_path") checkout_id: String? = null,
        @Query("entity_id") entity_id: String? = null
    ): Deferred<Response<DefaultDataModel>>
//     "https://eu-prod.oppwa.com/v1/checkouts"
//     "https://eu-test.oppwa.com/v1/checkouts"
    @FormUrlEncoded
    @POST("https://eu-test.oppwa.com/v1/checkouts")
    fun getHyperPayCheckOutId(
        @Query("entityId") entityId: String? = null,
        @Query("amount") amount: String? = null,
        @Query("currency") currency: String? = null,
        @Query("merchantTransactionId") merchantTransactionId: String? = null,
        @Query("customer.email") email: String? = null,
        @Query("customer.phone") phone: String? = null,
        @Query("customer.mobile") mobile: String? = null,
        @Query("billing.street1") street1: String? = null,
        @Query("billing.country") country: String? = null,
        @Query("billing.state") state: String? = null,
        @Query("customer.givenName") givenName: String? = null,
        @Query("customer.surname") surname: String? = null,
        @Query("billing.postcode") postcode: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("notificationUrl") notificationUrl: String? = null,
        @Field("customParameters[transaction_id]") transactionId: String? = null,
        @Field("customParameters[type]") type: String? = null,
        @Field("customParameters[order_id]") orderId: String? = null,
        @Field("customParameters[payment_method_id]") payment_method_id: String? = null,
    ): Deferred<Response<DefaultPayment>>

    @FormUrlEncoded
    @POST("https://eu-test.oppwa.com/v1/checkouts")
    fun getHyperPayCheckOutId(
        @Query("entityId") entityId: String? = null,
        @Query("amount") amount: String? = null,
        @Query("currency") currency: String? = null,
        @Query("merchantTransactionId") merchantTransactionId: String? = null,
        @Query("customer.email") email: String? = null,
        @Query("customer.phone") phone: String? = null,
        @Query("customer.mobile") mobile: String? = null,
        @Query("billing.street1") street1: String? = null,
        @Query("billing.country") country: String? = null,
        @Query("billing.state") state: String? = null,
        @Query("customer.givenName") givenName: String? = null,
        @Query("customer.surname") surname: String? = null,
        @Query("billing.postcode") postcode: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("notificationUrl") notificationUrl: String? = null,
        @Field("customParameters[transaction_id]") transactionId: String? = null,
        @Field("customParameters[address_id]") address_id: String? = null,
        @Field("customParameters[code]") code: String? = null,
        @Field("customParameters[payment_method_id]") payment_method_id: String? = null,
        @Field("customParameters[receive_from_store]") receive_from_store: String? = null,
        @Field("customParameters[user_id]") user_id: String? = null,
        @Field("customParameters[type]") type: String? = null,
    ): Deferred<Response<DefaultPayment>>


}