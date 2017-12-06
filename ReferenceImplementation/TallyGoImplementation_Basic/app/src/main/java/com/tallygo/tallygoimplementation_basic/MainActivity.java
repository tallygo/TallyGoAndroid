package com.tallygo.tallygoimplementation_basic;

//Copyright (c) 2017 TallyGo
//Created by haydenchristensen on 10/26/17
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.
//
//Note that this license only applies to the contents of this file,
//and not the entirety of the SDK

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tallygo.tallygoandroid.activities.tallygo.TallyGoActivity;

/**
 * This activity shows the most basic way to implement TallyGo's SDK. All we need to do is launch
 * the TallyGoActivity and boom! Your user has a fully functioning Map, Search and Turn-by-Turn
 * navigation application. Of course, this will not be useful if you would like to specify
 * map functionality or provide a route of your own... We have other reference implementations for
 * that!
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Here we create a view and a button to start TallyGo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startTallyGoButton = findViewById(R.id.b_start_tallygo);
        startTallyGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTallyGo();
            }
        });
    }

    /**
     * And presto! You have navigation!
     *
     * Note that this kills the current activity, and brings the user into the fully pre-built
     * TallyGo search and navigation system. While you could build some logic around this, it would
     * probably be easier that you check out our other implementations for further customization
     * (such as {@link com.tallygo.tallygoandroid.activities.turnbyturn.TGTurnByTurnActivity})
     *
     * You can find further examples in our reference implementation repo
     * @see <a href="https://github.com/tallygo/TallyGoAndroid">https://github.com/tallygo/TallyGoAndroid</a>
     */
    private void startTallyGo() {
        Intent intent = new Intent(this, TallyGoActivity.class);
        startActivity(intent);
        finish();
    }
}
