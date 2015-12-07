package com.colinchartier.overture.app.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.colinchartier.overture.app.R;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CreatePlaylistDialog extends DialogFragment {
    private static final String LOG_TAG = "CREATE_PLAYLIST";

    private Listener listener;
    private AlertDialog alert;

    private String playlistName = "";
    private Set<String> existingPlaylistNames = new HashSet<String>();
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("^\\s+$");


    private boolean createPlaylistButtonEnabled = true;

    @Deprecated
    public CreatePlaylistDialog() {
        super();
    }

    public static CreatePlaylistDialog newInstance() {
        return new CreatePlaylistDialog();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setExistingPlaylistNames(Set<String> existingPlaylistNames) {
        this.existingPlaylistNames = existingPlaylistNames;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_playlist, null);
        EditText setNameEditText = (EditText) view.findViewById(R.id.create_playlist_name);
        setNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        setNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                playlistName = s.toString();
                checkValidPlaylistName();
            }
        });
        checkValidPlaylistName();

        alert = (new AlertDialog.Builder(getActivity())).setMessage("Create Playlist")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onFinish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                checkValidPlaylistName();
            }
        });
        return alert;
    }

    private void onFinish() {
        if (playlistNameValid(playlistName)) {
            listener.onPlaylistCreated(playlistName);
            dismiss();
        } else {
            Log.e(LOG_TAG, "Somehow managed to click 'accept' on an invalid playlist name!");
            dismiss();
        }
    }

    private void checkValidPlaylistName() {
        setCreatePlaylistButtonEnabled(playlistNameValid(playlistName));
    }

    private void setCreatePlaylistButtonEnabled(boolean enabled) {
        if (alert != null) {
            Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            if (b != null) {
                b.setEnabled(enabled);
            }
        }
    }

    private boolean playlistNameValid(String playlistName) {
        if (playlistName == null || playlistName.length() == 0) {
            return false;
        }
        if (existingPlaylistNames.contains(playlistName)) { //Non-unique name
            return false;
        }
        return !WHITESPACE_PATTERN.matcher(playlistName).matches();
    }

    public interface Listener {
        void onPlaylistCreated(String playlistName);
    }
}
