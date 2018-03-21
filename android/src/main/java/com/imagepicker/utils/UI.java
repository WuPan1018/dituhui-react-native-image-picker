package com.imagepicker.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.facebook.react.bridge.ReadableMap;
import com.imagepicker.ImagePickerModule;
import com.imagepicker.R;
import com.imagepicker.dialog.PersonAvatarDialog;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Alexander Ustinov
 */
public class UI
{
    public static @NonNull
    PersonAvatarDialog chooseDialog(@Nullable final ImagePickerModule module,
                        @NonNull final ReadableMap options,
                        @Nullable final OnAction callback)
    {
        final Context context = module.getActivity();
        if (context == null)
        {
            return null;
        }
        final WeakReference<ImagePickerModule> reference = new WeakReference<>(module);

        final ButtonsHelper buttons = ButtonsHelper.newInstance(options);
        final List<String> titles = buttons.getTitles();
        final List<String> actions = buttons.getActions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                R.layout.list_item,
                titles
        );
//       AlertDialog.Builder builder = new AlertDialog.Builder(context, module.getDialogThemeId() *//*android.R.style.Theme_Holo_Light_Dialog*//*);
//        if (ReadableMapUtils.hasAndNotEmptyString(options, "title"))
//        {
//            builder.setTitle(options.getString("title"));
//        }

//        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int index) {
//                final String action = actions.get(index);
//
//                switch (action) {
//                    case "photo":
//                        callback.onTakePhoto(reference.get());
//                        break;
//
//                    case "library":
//                        callback.onUseLibrary(reference.get());
//                        break;
//
//                    case "cancel":
//                        callback.onCancel(reference.get());
//                        break;
//
//                    default:
//                        callback.onCustomButton(reference.get(), action);
//                }
//            }
//        });

//        builder.setNegativeButton(buttons.btnCancel.title, new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialogInterface,
//                                int i)
//            {
//                callback.onCancel(reference.get());
//                dialogInterface.dismiss();
//            }
//        });

//        final AlertDialog dialog = builder.create();

//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
//        {
//            @Override
//            public void onCancel(@NonNull final DialogInterface dialog)
//            {
//                callback.onCancel(reference.get());
//                dialog.dismiss();
//            }
//        });

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final PersonAvatarDialog.Builder builder=new PersonAvatarDialog.Builder(context);
        PersonAvatarDialog dialog= builder.build();
        builder.setAdapter(adapter);
        builder.setCancelText(buttons.btnCancel.title);
        builder.setOnOnItemClickListener(new PersonAvatarDialog.OnOnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final String action = actions.get(index);

                switch (action) {
                    case "photo":
                        callback.onTakePhoto(reference.get());
                        builder. close();
                        break;

                    case "library":
                        callback.onUseLibrary(reference.get());
                        builder. close();
                        break;

                    case "cancel":
                        callback.onCancel(reference.get());
                        builder. close();
                        break;

                    default:
                        callback.onCustomButton(reference.get(), action);
                        builder. close();
                }
            }

            @Override
            public void onCancel() {
//                callback.onCancel(reference.get());
                builder. close();
            }
        });


        return dialog;
    }

    public interface OnAction
    {
        void onTakePhoto(@Nullable ImagePickerModule module);
        void onUseLibrary(@Nullable ImagePickerModule module);
        void onCancel(@Nullable ImagePickerModule module);
        void onCustomButton(@Nullable ImagePickerModule module, String action);
    }
}
