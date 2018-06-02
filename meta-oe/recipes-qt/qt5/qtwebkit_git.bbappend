SRC_URI += " \
        file://0001-Expose-WebSecurityEnabled-via-Qt.patch \
        file://0002-Add-HbbTV-Mimetypes.patch \
        file://0003-always-emit-keypress-event-with-keycode.patch \
        file://0004-Enable-NPAPI-for-Qt-without-X11.patch \
"

SRC_URI_append_dm900 += " \
        file://0006-ANGLE-remove-EGL-GLES2-KHR-headers.patch \
"

SRC_URI_append_dm920 += " \
        file://0006-ANGLE-remove-EGL-GLES2-KHR-headers.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwebkit-git:"

# Qt packages are machine specific
QT_PACKAGES_ARCH = "${MACHINE_ARCH}"
