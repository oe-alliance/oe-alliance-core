#!/bin/sh

HPD_STATE=/sys/class/amhdmitx/amhdmitx0/hpd_state
DISP_CAP=/sys/class/amhdmitx/amhdmitx0/disp_cap
DISP_MODE=/sys/class/display/mode


bpp=32
mode=720p50hz
hdmi=`cat $HPD_STATE`
if [ $hdmi -eq 1 ]; then
    echo $mode > $DISP_MODE
fi

outputmode=$mode

common_display_setup() {
#    fbset -fb /dev/fb1 -g 32 32 32 32 32
    echo $outputmode > /sys/class/display/mode
    sleep 1
    echo 0 > /sys/class/ppmgr/ppscaler
    echo 0 > /sys/class/graphics/fb0/free_scale
    echo 1 > /sys/class/graphics/fb0/freescale_mode

    case $outputmode in
            800x480*) M="0 0 799 479" ;;
            vga*)  M="0 0 639 749" ;;
            800x600p60*) M="0 0 799 599" ;;
            1024x600p60h*) M="0 0 1023 599" ;;
            1024x768p60h*) M="0 0 1023 767" ;;
            sxga*) M="0 0 1279 1023" ;;
            1440x900p60*) M="0 0 1439 899" ;;
            480*) M="0 0 719 479" ;;
            576*) M="0 0 719 575" ;;
            720*) M="0 0 1279 719" ;;
            800*) M="0 0 1279 799" ;;
            1080*) M="0 0 1919 1079" ;;
            1920x1200*) M="0 0 1919 1199" ;;
            1680x1050p60*) M="0 0 1679 1049" ;;
        1360x768p60*) M="0 0 1359 767" ;;
        1366x768p60*) M="0 0 1365 767" ;;
        1600x900p60*) M="0 0 1599 899" ;;
    esac

#    echo $M > /sys/class/graphics/fb0/free_scale_axis
#    echo $M > /sys/class/graphics/fb0/window_axis

    echo "0 0 1280 720 0 0 18 18" > /sys/class/display/axis
#    echo "0 0 1279 719" > /sys/class/video/axis
#    echo "0 0 1279 719 0" > /sys/class/ppmgr/ppscaler_rect
#    echo 0 > /sys/class/graphics/fb0/free_scale

#   echo 0x10001 > /sys/class/graphics/fb0/free_scale
#   echo 0x10001 > /sys/class/graphics/fb1/free_scale

}

case $mode in
    800x480*)   fbset -fb /dev/fb0 -g 800 480 800 960 $bpp; common_display_setup ;;
    vga*)       fbset -fb /dev/fb0 -g 640 480 640 960 $bpp; common_display_setup ;;
    480*)       fbset -fb /dev/fb0 -g 720 480 720 960 $bpp; common_display_setup ;;
    800x600p60*)    fbset -fb /dev/fb0 -g 800 600 800 1200 $bpp;    common_display_setup ;;
    576*)       fbset -fb /dev/fb0 -g 720 576 720 1152 $bpp;    common_display_setup ;;
    1024x600p60h*)  fbset -fb /dev/fb0 -g 1024 600 1024 1200 $bpp;  common_display_setup ;;
    1024x768p60h*)  fbset -fb /dev/fb0 -g 1024 768 1024 1536 $bpp;  common_display_setup ;;
    720*)       fbset -fb /dev/fb0 -g 1280 720 1280 1440 $bpp;  common_display_setup ;;
    800*)       fbset -fb /dev/fb0 -g 1280 800 1280 1600 $bpp;  common_display_setup ;;
    sxga*)      fbset -fb /dev/fb0 -g 1280 1024 1280 2048 $bpp; common_display_setup ;;
    1440x900p60*)   fbset -fb /dev/fb0 -g 1440 900 1440 1800 $bpp;  common_display_setup ;;
    1080*)      fbset -fb /dev/fb0 -g 1920 1080 1920 2160 $bpp; common_display_setup ;;
    1920x1200*) fbset -fb /dev/fb0 -g 1920 1200 1920 2400 $bpp; common_display_setup ;;
    1360x768p60*)   fbset -fb /dev/fb0 -g 1360 768 1360 1536 $bpp;  common_display_setup ;;
    1366x768p60*)   fbset -fb /dev/fb0 -g 1366 768 1366 1536 $bpp;  common_display_setup ;;
    1600x900p60*)   fbset -fb /dev/fb0 -g 1600 900 1600 1800 $bpp;  common_display_setup ;;
    1680x1050p60*)  fbset -fb /dev/fb0 -g 1680 1050 1680 2100 $bpp; common_display_setup ;;

esac


# Console unblack
echo 0 > /sys/class/graphics/fb0/blank
echo 1 > /sys/class/graphics/fb1/blank

#pbsplash
/usr/bin/remotecfg /etc/amremote/wetek.conf

echo rm default > /sys/class/vfm/map
echo add default decoder ppmgr deinterlace amvideo > /sys/class/vfm/map
#echo 912000 >   /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq
echo conservative > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor
echo 1488000 >  /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq
ln -sf /sys/class/leds/led-sys/brightness /dev/led0
ln -sf /sys/class/leds/wetek\:blue\:ethled/brightness /dev/led1
ln -sf /sys/class/leds/wetek\:blue\:wifiled/brightness /dev/led2

export ROOT=
# Parse command line options
for x in $(cat /proc/cmdline); do
	case $x in
	root=*)
		ROOT=${x#root=}
		;;
	esac
done

# only once
if [ ! -e /etc/.sdpart ] && [ "$ROOT" = "/dev/mmcblk0p2" ]; then
	parted /dev/mmcblk0 unit MB mkpart primary ext4 1100MB 95%
	sync ; sync ;
	umount -f /dev/mmcblk0p3
	/sbin/mkfs.ext4 -L mSD_Extra /dev/mmcblk0p3
	umount -f /dev/mmcblk0p3
	sync ; sync ;
	tune2fs -c 5 -o journal_data_writeback /dev/mmcblk0p3
	tune2fs -o ^acl /dev/mmcblk0p3
	tune2fs -o ^user_xattr /dev/mmcblk0p3
	tune2fs -E  mount_opts=noatime  /dev/mmcblk0p3
#	tune2fs -E mount_opts=auto_da_alloc  /dev/mmcblk0p3
#	tune2fs -O ^has_journal  /dev/mmcblk0p3
	/usr/sbin/partprobe 
	sync ; sync ;
	mkdir /media/mSD_Extra
	mount -t ext4 -O noatime,nodiratime /dev/mmcblk0p3 /media/mSD_Extra
	cd /media/mSD_Extra
	mkdir -m 777 movie
	mkdir -m 777 timeshift
	touch /etc/.sdpart
fi

if [ ! -e /etc/.aml ]; then
	touch /etc/.aml
	cp /etc/asound.conf.aml /etc/asound.conf
fi


