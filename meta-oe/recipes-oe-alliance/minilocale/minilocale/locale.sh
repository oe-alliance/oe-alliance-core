export LC_TELEPHONE="C.UTF-8"
export LC_MONETARY="C.UTF-8"
export LC_ADDRESS="C.UTF-8"
export LC_PAPER="C.UTF-8"
export LC_CTYPE="C.UTF-8"
export LC_IDENTIFICATION="C.UTF-8"
export LC_MEASUREMENT="C.UTF-8"
export LC_MESSAGES="C.UTF-8"
export LC_NUMERIC="C.UTF-8"
export LC_NAME="C.UTF-8"
export LC_DATE="C.UTF-8"
export LC_TIME="C.UTF-8"
export LANG="C.UTF-8"

# check lib64
libdir=/usr/lib
if [ -d /usr/lib64 ] ; then
	libdir=/usr/lib64
fi

# source the user/distro config
[ -f ~/.config/locale.conf ] && . ~/.config/locale.conf

# Per language LC_COLLATE have been deleted (or are only symlinks), enforce C.UTF-8 or POSIX.
# Prefer C.UTF-8 over POSIX, but 64 MB flash only has the built-in POSIX COLLATE:
[ -f $libdir/locale/C.UTF-8/LC_COLLATE ] && export LC_COLLATE="C.UTF-8" || export LC_COLLATE="POSIX"
export LC_ALL=""
