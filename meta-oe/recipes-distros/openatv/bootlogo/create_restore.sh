#!/bin/bash

black=0

sanity_fail () {
	echo ERROR: Missing tools ...
	echo
	echo You might want to:
	echo sudo apt install -y imagemagick ; sudo apt install -y mjpegtools
	echo
	echo Exiting.
	exit 1
}


sanity_check () {
        echo Performing sanity check:
        echo
        echo Checking for for/do ...
        if ! type for >/dev/null 2>&1; then
                sanity_fail
        fi
        if ! type do >/dev/null 2>&1; then
                sanity_fail
        fi
        for COMMAND in composite jpeg2yuv mpeg2enc
        do
                echo Checking for $COMMAND ...
                if ! type $COMMAND >/dev/null 2>&1; then
                        sanity_fail
                fi
        done
}

overlay () {
	[[ $black = 0 ]] && composite -gravity center restore_overlay.png bootlogo.jpg restore.jpg
	[[ $black = 1 ]] && composite -gravity center restore_overlay_black.png bootlogo.jpg restore.jpg
}

jpg2mvi () {
	jpeg2yuv -v 0 -f 25 -n1 -I p -j restore.jpg | mpeg2enc -v 0 -f 12 -x 1280 -y 720 -o restore_new.mvi
}

sanity_check
[ "$1" = "black" ] && black=1
pushd ./files >/dev/null
overlay
jpg2mvi

if [ -f ./restore_new.mvi ]; then
	rm restore.mvi
	rm restore.jpg
	mv restore_new.mvi restore.mvi
fi

popd >/dev/null
