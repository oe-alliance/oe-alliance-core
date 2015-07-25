#!/bin/sh

bpp=32
mode=720p

for x in $(cat /proc/cmdline); do
    case ${x} in
        m_bpp=*) export bpp=${x#*=} ;;
        hdmimode=*) export mode=${x#*=} ;;
    esac
done

case $mode in
    800x480*)   fbset -fb /dev/fb0 -g 800 480 800 960 $bpp ;;
    vga*)       fbset -fb /dev/fb0 -g 640 480 640 960 $bpp ;;
    480*)       fbset -fb /dev/fb0 -g 720 480 720 960 $bpp ;;
    800x600p60*)    fbset -fb /dev/fb0 -g 800 600 800 1200 $bpp ;;
    576*)       fbset -fb /dev/fb0 -g 720 576 720 1152 $bpp ;;
    1024x600p60h*)  fbset -fb /dev/fb0 -g 1024 600 1024 1200 $bpp ;;
    1024x768p60h*)  fbset -fb /dev/fb0 -g 1024 768 1024 1536 $bpp ;;
    720*)       fbset -fb /dev/fb0 -g 1280 720 1280 1440 $bpp ;;
    800*)       fbset -fb /dev/fb0 -g 1280 800 1280 1600 $bpp ;;
    sxga*)      fbset -fb /dev/fb0 -g 1280 1024 1280 2048 $bpp ;;
    1440x900p60*)   fbset -fb /dev/fb0 -g 1440 900 1440 1800 $bpp ;;
    1080*)      fbset -fb /dev/fb0 -g 1920 1080 1920 2160 $bpp ;;
    1920x1200*) fbset -fb /dev/fb0 -g 1920 1200 1920 2400 $bpp ;;
    1360x768p60*)   fbset -fb /dev/fb0 -g 1360 768 1360 1536 $bpp ;;
    1366x768p60*)   fbset -fb /dev/fb0 -g 1366 768 1366 1536 $bpp ;;
    1600x900p60*)   fbset -fb /dev/fb0 -g 1600 900 1600 1800 $bpp ;;
    1680x1050p60*)  fbset -fb /dev/fb0 -g 1680 1050 1680 2100 $bpp ;;
esac
