package agenda.celsoandre.com.br.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import agenda.celsoandre.com.br.agenda.R;
import agenda.celsoandre.com.br.agenda.dao.AlunoDAO;

/**
 * Created by Celso André on 07/01/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String fotmato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, fotmato);
        String telefone = sms.getDisplayOriginatingAddress();
        Log.i("fone", telefone);
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.ehAluno(telefone)){
            Toast.makeText(context, "o numero é " +telefone, Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        Toast.makeText(context, "o numero é " +telefone, Toast.LENGTH_SHORT).show();
    }
}
