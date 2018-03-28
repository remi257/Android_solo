package com.example.remi.androidmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDialogFragment extends DialogFragment {

    public boolean OK = false;
    public String Text = "";
    private OnFragmentInteractionListener mListener;
    private static OKClickAction okaction;
    public interface OKClickAction{
        void OnOKClick(String str);
    }

    public EditDialogFragment() {
        // Required empty public constructor
    }



    public static EditDialogFragment newInstance(OKClickAction okclick) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        okaction = okclick;
        fragment.setArguments(args);
//

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button)view.findViewById(R.id.ButtonOK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Text = ((EditText)getDialog().findViewById(R.id.EditTextField)).getText().toString();
                okaction.OnOKClick(Text);
                dismiss();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
