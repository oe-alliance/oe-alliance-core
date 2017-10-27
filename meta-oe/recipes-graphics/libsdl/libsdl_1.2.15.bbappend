FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "--disable-static --enable-cdrom --enable-threads --enable-timers \
                --enable-file --disable-oss --disable-esd --disable-arts \
                --disable-diskaudio --disable-nas --disable-esd-shared --disable-esdtest \
                --disable-mintaudio --disable-nasm --disable-video-dga \
                ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-video-fbcon", "--disable-video-fbcon", d)} \
                --disable-video-ps2gs --disable-video-ps3 \
                --disable-xbios --disable-gem --disable-video-dummy \
                --enable-input-events --enable-input-tslib --enable-pthreads \
                ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '--enable-video-opengl', '--disable-video-opengl', d)} \
                ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '--enable-video-x11', '--disable-video-x11', d)} \
                --disable-video-svga \
                --disable-video-picogui --disable-video-qtopia --enable-sdl-dlopen \
                --disable-rpath \
                --disable-pulseaudio \
"

SRC_URI_append = " \
           file://Add_key_symbols.patch \
"


do_qa_staging() {
}
