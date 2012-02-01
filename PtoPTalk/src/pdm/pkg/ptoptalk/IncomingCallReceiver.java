package pdm.pkg.ptoptalk;


import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.net.sip.*;

/**
* Listens for incoming SIP calls, intercepts and hands them off to WalkieTalkieActivity.
*/
public class IncomingCallReceiver extends BroadcastReceiver {
   /**
    * Processes the incoming call, answers it, and hands it over to the
    * WalkieTalkieActivity.
    * @param context The context under which the receiver is running.
    * @param intent The intent being received.
    */
   @Override
   public void onReceive(Context context, Intent intent) {
       SipAudioCall incomingCall = null;
       try {

           SipAudioCall.Listener listener = new SipAudioCall.Listener() {
               @Override
               public void onRinging(SipAudioCall call, SipProfile caller) {
                   try {
                       call.answerCall(30);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           };

           WalkieTalkieActivity wtActivity = (WalkieTalkieActivity) context;

           incomingCall = wtActivity.manager.takeAudioCall(intent, listener);
           incomingCall.answerCall(30);
           incomingCall.startAudio();
           incomingCall.setSpeakerMode(true);
           if(incomingCall.isMuted()) {
               incomingCall.toggleMute();
           }

           wtActivity.call = incomingCall;

           wtActivity.updateStatus(incomingCall);

       } catch (Exception e) {
           if (incomingCall != null) {
               incomingCall.close();
           }
       }
   }

}