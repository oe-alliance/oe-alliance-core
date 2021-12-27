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
	[[ $black = 0 ]] && composite -gravity center overlay_hd.png bootlogo_hd.jpg restore_hd.jpg
	[[ $black = 0 ]] && composite -gravity center overlay_fhd.png bootlogo_fhd.jpg restore_fhd.jpg
	[[ $black = 1 ]] && composite -gravity center overlay_black_hd.png bootlogo_hd.jpg restore_hd.jpg
	[[ $black = 1 ]] && composite -gravity center overlay_black_fhd.png bootlogo_fhd.jpg restore_fhd.jpg
}

jpg2mvi () {
	jpeg2yuv -v 0 -f 25 -n1 -I p -j restore_hd.jpg | mpeg2enc -v 0 -f 12 -x 1280 -y  720 -a 3 -4 1 -2 1 -q 1 -H --level high -o restore_new_HD.mvi
	jpeg2yuv -v 0 -f 25 -n1 -I p -j restore_fhd.jpg | mpeg2enc -v 0 -f 13 -x 1920 -y 1080 -a 3 -4 1 -2 1 -q 1 -H --level high -o restore_new_FDH.mvi
}

sanity_check
[ "$1" = "black" ] && black=1
pushd ./files >/dev/null
overlay
jpg2mvi

if [ -f ./restore_new_hd.mvi ]; then
	rm restore_hd.mvi
	mv restore_new_hd.mvi restore_hd.mvi
fi

if [ -f ./restore_new_fhd.mvi ]; then
	rm restore_fhd.mvi
	mv restore_new_fhd.mvi restore_fhd.mvi
fi

popd >/dev/null
