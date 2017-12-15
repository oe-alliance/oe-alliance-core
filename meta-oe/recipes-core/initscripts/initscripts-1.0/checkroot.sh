#! /bin/sh
### BEGIN INIT INFO
# Provides:          checkroot checkroot-bootclean mtab
# Required-Start:    mountdevsubfs hostname
# Required-Stop:     
# Should-Start:      keymap hwclockfirst hdparm bootlogd
# Should-stop:
# Default-Start:     S
# Default-Stop:
# X-Interactive:     true
# Short-Description: Check to root file system.
### END INIT INFO

. /etc/default/rcS

#
# Set SULOGIN in /etc/default/rcS to yes if you want a sulogin to be spawned
# from this script *before anything else* with a timeout, like SCO does.
#
test "$SULOGIN" = yes && sulogin -t 30 $CONSOLE

#
# Read /etc/fstab.
#
exec 9< /etc/fstab
rootmode=rw
rootopts=rw
rootcheck=$ENABLE_ROOTFS_FSCK
swap_on_md=no
devfs=
while read fs mnt type opts dump pass junk <&9
do
	case "$fs" in
		""|\#*)
			continue;
			;;
		/dev/md*)
			# Swap on md device.
			test "$type" = swap && swap_on_md=yes
			;;
		/dev/*)
			;;
		*)
			# Might be a swapfile.
			test "$type" = swap && swap_on_md=yes
			;;
	esac
	test "$type" = devfs && devfs="$fs"
	test "$mnt" != / && continue
	rootopts="$opts"
	test "$pass" = 0 -o "$pass" = "" && rootcheck=no
	case "$opts" in
		ro|ro,*|*,ro|*,ro,*)
			rootmode=ro
			;;
	esac
done
exec 0>&9 9>&-

# Check for conflicting configurations
if [ "$rootmode" = "ro" -a "$ROOTFS_READ_ONLY" = "no" ] || \
	[ "$rootmode" = "rw" -a "$ROOTFS_READ_ONLY" = "yes" ]; then
	echo ""
	echo "WARN: conflicting configurations in /etc/fstab and /etc/default/rcS"
	echo "      regarding the writability of rootfs. Please fix one of them."
	echo ""
fi


#
# Activate the swap device(s) in /etc/fstab. This needs to be done
# before fsck, since fsck can be quite memory-hungry.
#
test "$VERBOSE" != no && echo "Activating swap"
[ -x /sbin/swapon ] && swapon -a

#
# Check the root filesystem.
#
if test -f /fastboot || test $rootcheck = no
then
  test $rootcheck = yes && echo "Fast boot, no filesystem check"
else
  #
  # Ensure that root is quiescent and read-only before fsck'ing.
  #
  mount -n -o remount,ro /
  if test $? = 0
  then
    if test -f /forcefsck
    then
	force="-f"
    else
	force=""
    fi
    if test "$FSCKFIX" = yes
    then
	fix="-y"
    else
	fix="-a"
    fi
    spinner="-C"
    case "$TERM" in
        dumb|network|unknown|"") spinner="" ;;
    esac
    test `uname -m` = s390 && spinner="" # This should go away
    test "$VERBOSE" != no && echo "Checking root filesystem..."
    fsck $spinner $force $fix / || true
    #
    # Ignore the outcome of fsck as there is nothing we let alone the user could do.
    #
  else
    echo "*** ERROR!  Cannot fsck root fs because it is not mounted read-only!"
    echo
  fi
fi

#
#	If the root filesystem was not marked as read-only in /etc/fstab,
#	remount the rootfs rw but do not try to change mtab because it
#	is on a ro fs until the remount succeeded. Then clean up old mtabs
#	and finally write the new mtab.
#
mount -n -o remount,$rootmode /
if test "$rootmode" = rw
then
	ln -sf /proc/mounts /dev/mtab
fi

: exit 0
