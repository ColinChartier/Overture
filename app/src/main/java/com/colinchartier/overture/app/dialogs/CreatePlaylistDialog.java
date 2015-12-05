package com.colinchartier.overture.app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.dialogs.presenters.CreatePlaylistPresenter;
import com.colinchartier.overture.app.dialogs.views.CreatePlaylistView;
import com.colinchartier.overture.app.playlist.Playlist;

import java.util.List;

public class CreatePlaylistDialog extends DialogFragment implements CreatePlaylistView {
    private CreatePlaylistPresenter presenter;

    private AlertDialog alert;

    private boolean createPlaylistButtonEnabled = true;


    /**
     * Creates a new instance of a presenter playlist dialog fragment.<br>
     * Calling the constructor directly will cause errors! Use {@link #newInstance(CreatePlaylistPresenter)} instead!
     */
    @Deprecated
    public CreatePlaylistDialog() {
        super();
    }

    public static CreatePlaylistDialog newInstance(CreatePlaylistPresenter presenter) {
        CreatePlaylistDialog fragment = new CreatePlaylistDialog();
        fragment.presenter = presenter;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_playlist, null);
        EditText setNameEditText = (EditText) view.findViewById(R.id.create_playlist_name);
        setNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    presenter.onComplete();
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
                presenter.onPlaylistNameChanged(s.toString());
            }
        });
        presenter.init();

        alert = (new AlertDialog.Builder(getActivity())).setMessage("Create Playlist")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onComplete();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onCancel();
                    }
                }).create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                presenter.init();
            }
        });
        return alert;
    }

    @Override
    public void setCreatePlaylistButtonEnabled(boolean enabled) {
        if (alert != null) {
            Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            if (b != null) {
                b.setEnabled(enabled);
            }
        }
    }

    public void setPlaylistList(List<Playlist> playlistList) {
        presenter.setPlaylistList(playlistList);
    }
}
