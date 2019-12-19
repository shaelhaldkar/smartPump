package  com.trackingsystem.webServices

/**
 * Created by pradeep on 25/05/18.
 * Mail id : pradeep.kumar@inventum.net
 */
interface WebResponseListener {

    fun onResponseReceived(error: String?, response: String, tag: String?)
}
