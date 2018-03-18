package com.blackflagbin.kcommon.http.progress

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.os.Message

import com.blackflagbin.kcommon.R

class ProgressDialogHandler(
        private val context: Context,
        private val mProgressCancelListener: ProgressCancelListener,
        private val cancelable: Boolean) : Handler() {

    private var pd: Dialog? = null

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            SHOW_PROGRESS_DIALOG -> initProgressDialog()
            DISMISS_PROGRESS_DIALOG -> dismissProgressDialog()
        }
    }

    private fun initProgressDialog() {
        if (pd == null) {
            pd = Dialog(context, R.style.default_dialog)
            //返回键可以取消Dialog
            pd!!.setCancelable(cancelable)
            //点击外侧不可取消
            pd!!.setCanceledOnTouchOutside(false)

            if (cancelable) {
                pd!!.setOnCancelListener { mProgressCancelListener.onCancelProgress() }
            }

            if (pd != null && !pd!!.isShowing) {
                pd!!.show()
                pd!!.setContentView(R.layout.dialog_loading)
            }
        }
    }

    private fun dismissProgressDialog() {
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
            pd = null
        }
    }

    companion object {

        public val SHOW_PROGRESS_DIALOG = 1
        public val DISMISS_PROGRESS_DIALOG = 2
    }

}
