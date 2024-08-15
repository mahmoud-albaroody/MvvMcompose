package com.bitaqaty.reseller.utilities

import com.bitaqaty.reseller.data.localStorage.ProductModel
import com.bitaqaty.reseller.MainApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun deleteCartDB(compositeDisposable: CompositeDisposable, onDeletedCart: () -> Unit) {
    MainApplication.getInstance()?.database?.getProductDao()
        ?.emptyBag()
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe {
            onDeletedCart.invoke()
        }.let {
            it?.let { it1 -> compositeDisposable.add(it1) }
        }
}

fun addProductToBag(
    productModel: ProductModel,
    compositeDisposable: CompositeDisposable,
    onAddProductCart: () -> Unit
) {
    MainApplication.getInstance()?.database?.getProductDao()?.addProductToBag(
        productModel
    )?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe {
            onAddProductCart.invoke()
        }?.let {
            compositeDisposable.add(it)
        }
}

fun getAllProduct(
    compositeDisposable: CompositeDisposable,
    onGetAllProduct: (List<ProductModel>) -> Unit
) {
    MainApplication.getInstance()?.database?.getProductDao()
        ?.getAllProducts()
        ?.subscribeOn(
            Schedulers.io()
        )
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe {
            onGetAllProduct.invoke(it)
        }?.let {
            compositeDisposable.add(it)
        }
}