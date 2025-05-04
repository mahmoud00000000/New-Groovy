package com.homecookapp.user.data.reporsitory

import com.google.gson.JsonObject
import com.example.groovyshopping.data.remote.RetrofitApi
import com.homecookapp.user.data.remote.networkHandling.NetworkResult
import com.homecookapp.user.data.remote.networkHandling.NetworkStatus
//import com.homecookapp.user.screens.hyperPayment.HyperPayCredential.getPaymentTokenCredential
//import com.homecookapp.user.screens.product.details.presentation.CartDataRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Query


class MainRepository constructor(var apiService: RetrofitApi) : NetworkResult() {

    suspend fun getSetting(networkStatus: NetworkStatus) = getResult({
        apiService.getSetting().await()
    }, networkStatus)

    suspend fun getPage(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getPage(id).await()
    }, networkStatus)


    suspend fun getPages(networkStatus: NetworkStatus) = getResult({
        apiService.getPages().await()
    }, networkStatus)

    suspend fun getTransaction(networkStatus: NetworkStatus) = getResult({
        apiService.getTransaction().await()
    }, networkStatus)

    suspend fun chargeWallet(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.chargeWallet(data).await()
    }, networkStatus)

    suspend fun chargeCode(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.chargeCode(data).await()
    }, networkStatus)

    suspend fun getPaymentMethods(networkStatus: NetworkStatus, paymentType: String) = getResult({
        apiService.getPaymentMethods(paymentType).await()
    }, networkStatus)

    suspend fun getCountries(networkStatus: NetworkStatus) = getResult({
        apiService.getCountries().await()
    }, networkStatus)

    suspend fun login(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.login(data).await()
    }, networkStatus)

    suspend fun getHome(networkStatus: NetworkStatus) = getResult({
        apiService.getHome().await()
    }, networkStatus)

    suspend fun getMore(networkStatus: NetworkStatus, url: String) = getResult({
        apiService.getMore(url).await()
    }, networkStatus)

    suspend fun getSores(networkStatus: NetworkStatus, catId: Int?, subCatId: Int?) = getResult({
        apiService.getSores(catId, subCatId).await()
    }, networkStatus)

    suspend fun getSoresByCategory(networkStatus: NetworkStatus, catId: Int?) = getResult({
        apiService.getSoresByCategory(catId).await()
    }, networkStatus)

    suspend fun getAllCategories(networkStatus: NetworkStatus) = getResult({
        apiService.getAllCategories().await()
    }, networkStatus)


    suspend fun getStoreDetails(networkStatus: NetworkStatus, storeId: Int?, withProducts: Int?) =
        getResult({
            apiService.getStoreDetails(storeId, withProducts).await()
        }, networkStatus)

    suspend fun getProfile(networkStatus: NetworkStatus) = getResult({
        apiService.getProfile().await()
    }, networkStatus)


    suspend fun upDateProfile(networkStatus: NetworkStatus, email: String?, name: String) =
        getResult({
            var data = JsonObject()
            data.addProperty("email", email)
            data.addProperty("name", name)
            apiService.upDateProfile(
                data
            ).await()
        }, networkStatus)

    suspend fun upDatePhone(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.upDatePhone(
            data
        ).await()
    }, networkStatus)

    suspend fun verifyNewPhone(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.verifyNewPhone(
            data
        ).await()
    }, networkStatus)

    suspend fun deleteAccount(networkStatus: NetworkStatus) = getResult({
        apiService.deleteAccount().await()
    }, networkStatus)

    suspend fun getStores(
        networkStatus: NetworkStatus, active: Int? = null, categoryId: Int? = null
    ) = getResult({
        apiService.getStores(active, categoryId).await()
    }, networkStatus)

    suspend fun getStoreDetails(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getStoreDetails(id).await()
    }, networkStatus)

    suspend fun verifyPhone(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.verifyPhone(data).await()
    }, networkStatus)


    suspend fun getAddress(status: NetworkStatus) = getResult({
        apiService.getAddress().await()
    }, status)

    suspend fun checkAddressStore(status: NetworkStatus) = getResult({
        apiService.checkAddressStore().await()
    }, status)

    suspend fun deleteAddress(status: NetworkStatus, id: Int) = getResult({
        apiService.deleteAddress(id).await()
    }, status)


    suspend fun addAddress(
        status: NetworkStatus,
        title: String? = null,
        address: String? = null,
        description: String? = null,
        lat: String? = null,
        lng: String? = null,
        country_id: Int? = null,
        building_number: String? = null,
        floor: String? = null,
        apartment_number: String? = null
    ) = getResult({
        apiService.addAddress(
            title?.toRequestBody("text/plain".toMediaTypeOrNull()),
            address?.toRequestBody("text/plain".toMediaTypeOrNull()),
            description?.toRequestBody("text/plain".toMediaTypeOrNull()),
            country_id.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            lat?.toRequestBody("text/plain".toMediaTypeOrNull()),
            lng?.toRequestBody("text/plain".toMediaTypeOrNull()),
            building_number?.toRequestBody("text/plain".toMediaTypeOrNull()),
            floor?.toRequestBody("text/plain".toMediaTypeOrNull()),
            apartment_number?.toRequestBody("text/plain".toMediaTypeOrNull())
        ).await()
    }, status)


    suspend fun editAddress(
        status: NetworkStatus,
        id: String,
        title: String? = null,
        address: String? = null,
        description: String? = null,
        lat: String? = null,
        lng: String? = null,
        country_id: Int? = null
    ) = getResult({
        var type = "PUT"
        apiService.editAddress(
            id,
            title?.toRequestBody("text/plain".toMediaTypeOrNull()),
            address?.toRequestBody("text/plain".toMediaTypeOrNull()),
            description?.toRequestBody("text/plain".toMediaTypeOrNull()),
            country_id.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            lat?.toRequestBody("text/plain".toMediaTypeOrNull()),
            lng?.toRequestBody("text/plain".toMediaTypeOrNull()),
            type.toRequestBody("text/plain".toMediaTypeOrNull())
        ).await()
    }, status)

    suspend fun getProductDetails(networkStatus: NetworkStatus, productId: Int) = getResult({
        apiService.getProductDetails(productId).await()
    }, networkStatus)

    suspend fun addFav(networkStatus: NetworkStatus, model: String, modelId: Int) = getResult({
        apiService.addFav(model, modelId).await()
    }, networkStatus)

    suspend fun removeFav(networkStatus: NetworkStatus, model: String, modelId: Int) = getResult({
        apiService.removeFav(model, modelId).await()
    }, networkStatus)


    /*suspend fun addToCart(networkStatus: NetworkStatus, cartDataRequest: CartDataRequest) =
        getResult({

            val map = mutableMapOf<String, RequestBody>()

            map["store_id"] =
                cartDataRequest.store_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            map["product_id"] = cartDataRequest.product_id.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            map["qty"] =
                cartDataRequest.qty.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            map["note"] =
                cartDataRequest.note.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            cartDataRequest.options?.forEach { (key, value) ->
                map[key] = value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            }
            apiService.addToCart(
                map
            ).await()

        }*/ //, networkStatus)

    suspend fun prepareOrder(
        networkStatus: NetworkStatus,
        storeId: Int? = null,
        code: String? = null,
        receiveFromStore: Int? = null
    ) = getResult(
        {
            apiService.prepareOrder(
                storeId, code, receiveFromStore
            ).await()
        }, networkStatus
    )

    suspend fun deleteItemFromCart(networkStatus: NetworkStatus, itemI: Int) = getResult(
        {
            apiService.deleteItemFromCart(
                itemI
            ).await()
        }, networkStatus
    )

    suspend fun updateItemCountCart(networkStatus: NetworkStatus, itemI: Int, itemCount: Int) =
        getResult(
            {
                apiService.updateItemCountCart(
                    itemI, itemCount
                ).await()
            }, networkStatus
        )

    suspend fun makeOrder(
        networkStatus: NetworkStatus,
        storeId: Int? = null,
        code: String? = null,
        addressId: String? = null,
        paymentMethodId: String? = "0",
        receiveFromStore: Int? = null
    ) = getResult(
        {
            apiService.makeOrder(
                storeId, code, addressId, paymentMethodId, receiveFromStore
            ).await()
        }, networkStatus
    )

    suspend fun getOrdersList(networkStatus: NetworkStatus) =
        getResult({ apiService.getOrdersList().await() }, networkStatus)


    suspend fun getOrderDetails(networkStatus: NetworkStatus, orderId: Int) =
        getResult({ apiService.getOrderDetails(orderId).await() }, networkStatus)


    suspend fun reOrder(networkStatus: NetworkStatus, orderId: Int) =
        getResult({ apiService.reOrder(orderId).await() }, networkStatus)

    suspend fun payOrder(networkStatus: NetworkStatus, orderId: Int, paymentMethodId: Int) =
        getResult({ apiService.payOrder(orderId, paymentMethodId).await() }, networkStatus)

    suspend fun cancelOrder(networkStatus: NetworkStatus, orderId: Int, data: JsonObject) =
        getResult({ apiService.cancelOrder(orderId, data).await() }, networkStatus)

    suspend fun getCancellationReason(networkStatus: NetworkStatus) =
        getResult({ apiService.getCancellationReason().await() }, networkStatus)

    suspend fun getFavourites(networkStatus: NetworkStatus, model: String) =
        getResult({ apiService.getFavourites(model).await() }, networkStatus)

    suspend fun clearCart(networkStatus: NetworkStatus) =
        getResult({ apiService.clearCart().await() }, networkStatus)

    suspend fun getNotification(networkStatus: NetworkStatus) = getResult({
        apiService.getNotification().await()
    }, networkStatus)

    suspend fun sendSupportMsg(networkStatus: NetworkStatus, message: String) = getResult({
        apiService.sendSupportMsg("suggestion", message).await()
    }, networkStatus)

    suspend fun getOffers(networkStatus: NetworkStatus, page: Int) = getResult({
        apiService.getOffers(page).await()
    }, networkStatus)


    suspend fun sendPayment(networkStatus: NetworkStatus, checkout_id: String, entity_id: String) =
        getResult({
            apiService.sendPayment(
                checkout_id,
                entity_id
            ).await()
        }, networkStatus)


    suspend fun getHyperPayCheckOutId(
        networkStatus: NetworkStatus,
        entityId: String? = null,
        amount: String? = null,
        currency: String? = null,
        merchantTransactionId: String? = null,
        email: String? = null,
        phone: String? = null,
        mobile: String? = null,
        street1: String? = null,
        country: String? = null,
        state: String? = null,
        givenName: String? = null,
        surname: String? = null,
        postcode: String? = null,
        paymentType: String? = null,
        notificationUrl: String? = null,
        transactionId: String? = null,
        type: String? = null,
        orderId: String? = null,
        payment_method_id: String? = null,
    ) =
        getResult({
            apiService.getHyperPayCheckOutId(
                entityId,
                amount,
                currency,
                merchantTransactionId,
                email,
                phone,
                mobile,
                street1,
                country,
                state,
                givenName,
                surname,
                postcode,
                paymentType,
                notificationUrl,
                transactionId,
                type,
                orderId,
                payment_method_id
            ).await()
        }, networkStatus)

    suspend fun getHyperPayCheckOutId(
        networkStatus: NetworkStatus,
        entityId: String? = null,
        amount: String? = null,
        currency: String? = null,
        merchantTransactionId: String? = null,
        email: String? = null,
        phone: String? = null,
        mobile: String? = null,
        street1: String? = null,
        country: String? = null,
        state: String? = null,
        givenName: String? = null,
        surname: String? = null,
        postcode: String? = null,
        paymentType: String? = null,
        notificationUrl: String? = null,
        transactionId: String? = null,
        address_id: String? = null,
        code: String? = null,
        payment_method_id: String? = null,
        receive_from_store: String? = null,
        user_id: String? = null,
        type: String? = null,
    ) =
        getResult({
            apiService.getHyperPayCheckOutId(
                entityId,
                amount,
                currency,
                merchantTransactionId,
                email,
                phone,
                mobile,
                street1,
                country,
                state,
                givenName,
                surname,
                postcode,
                paymentType,
                notificationUrl,
                transactionId,
                address_id, code, payment_method_id, receive_from_store, user_id, type
            ).await()
        }, networkStatus)


}