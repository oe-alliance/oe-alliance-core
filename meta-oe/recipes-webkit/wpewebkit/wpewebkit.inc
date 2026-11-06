SUMMARY = "Lightweight WebKit port for embedded devices with OpenGL-ES acceleration"
DESCRIPTION = "WPE WebKit port pairs the WebKit engine with OpenGL-ES (OpenGL for Embedded Systems), \
               allowing embedders to create simple and performant systems based on Web platform technologies. \
               It is designed with hardware acceleration in mind, relying on EGL, and OpenGL ES."
HOMEPAGE = "https://wpewebkit.org/"
BUGTRACKER = "https://bugs.webkit.org/"
LICENSE = "BSD-2-Clause AND LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Source/WebCore/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 "

REQUIRED_DISTRO_FEATURES = "opengl"

DEPENDS:append = " \
    libsoup \
    bison-native gperf-native harfbuzz-native libxml2-native ccache-native ninja-native ruby-native \
    fontconfig freetype glib-2.0 harfbuzz icu jpeg pcre sqlite3 zlib libpng libtasn1 \
    libwebp libxml2 libxslt virtual/egl virtual/libgles2 libepoxy libgcrypt \
    unifdef-native \
"

inherit cmake features_check pkgconfig perlnative python3native


export WK_USE_CCACHE = "NO"

PACKAGECONFIG ??= "accessibility avif dfg-jit gamepad gbm gpu-process \
                   hyphen jit jpegxl libbacktrace \
                   mediasource mediastream \
                   remote-inspector speech-synthesis spellcheck \
                   sysprof \
                   ${@' system-sysprof' \
                       if bb.utils.contains('BBFILE_COLLECTIONS', 'meta-gnome', True, False, d) \
                       else '' } \
                   unified-builds video webaudio webdriver woff2 \
                   wpe-legacy-api wpe-platform \
                   wpe-platform-drm wpe-platform-headless \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wpe-platform-wayland', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'journald', '' ,d)} \
                  "

PACKAGECONFIG[reduce-size] = "-DCMAKE_BUILD_TYPE=MinSizeRel,-DCMAKE_BUILD_TYPE=Release,,"
PACKAGECONFIG[release-with-debug-info] = "-DCMAKE_BUILD_TYPE=RelWithDebInfo,-DCMAKE_BUILD_TYPE=Release,,"

# WPE features
PACKAGECONFIG[accessibility] = "-DUSE_ATK=ON,-DUSE_ATK=OFF,atk at-spi2-atk"
PACKAGECONFIG[avif] = "-DUSE_AVIF=ON,-DUSE_AVIF=OFF,libavif"
PACKAGECONFIG[bubblewrap] = "-DENABLE_BUBBLEWRAP_SANDBOX=ON -DBWRAP_EXECUTABLE=${bindir}/bwrap -DDBUS_PROXY_EXECUTABLE=${bindir}/xdg-dbus-proxy,-DENABLE_BUBBLEWRAP_SANDBOX=OFF,bubblewrap xdg-dbus-proxy libseccomp"
PACKAGECONFIG[developer-mode] = "-DDEVELOPER_MODE=ON,-DDEVELOPER_MODE=OFF,wayland-native wayland-protocols wpebackend-fdo"
PACKAGECONFIG[deviceorientation] = "-DENABLE_DEVICE_ORIENTATION=ON,-DENABLE_DEVICE_ORIENTATION=OFF,"
PACKAGECONFIG[dfg-jit] = "-DENABLE_DFG_JIT=ON,-DENABLE_DFG_JIT=OFF,"
PACKAGECONFIG[documentation] = "-DENABLE_DOCUMENTATION=ON,-DENABLE_DOCUMENTATION=OFF, gi-docgen-native gi-docgen"
PACKAGECONFIG[encryptedmedia] = "-DENABLE_ENCRYPTED_MEDIA=ON,-DENABLE_ENCRYPTED_MEDIA=OFF,libgcrypt"
PACKAGECONFIG[experimental-features] = "-DENABLE_EXPERIMENTAL_FEATURES=ON,-DENABLE_EXPERIMENTAL_FEATURES=OFF,libavif libjxl"
PACKAGECONFIG[gamepad] = "-DENABLE_GAMEPAD=ON,-DENABLE_GAMEPAD=OFF,libmanette"
PACKAGECONFIG[gbm] = "-DUSE_GBM=ON,-DUSE_GBM=OFF,libdrm"
PACKAGECONFIG[geolocation] = "-DENABLE_GEOLOCATION=ON,-DENABLE_GEOLOCATION=OFF,geoclue"
PACKAGECONFIG[gpu-process] = "-DENABLE_GPU_PROCESS=ON,-DENABLE_GPU_PROCESS=OFF,"
PACKAGECONFIG[hyphen] = "-DUSE_LIBHYPHEN=ON,-DUSE_LIBHYPHEN=OFF,hyphen"
PACKAGECONFIG[introspection] = "-DENABLE_INTROSPECTION=ON,-DENABLE_INTROSPECTION=OFF, gobject-introspection-native"
PACKAGECONFIG[jit] = "-DENABLE_JIT=ON,-DENABLE_JIT=OFF,"
PACKAGECONFIG[jpegxl] = "-DUSE_JPEGXL=ON,-DUSE_JPEGXL=OFF,libjxl"
PACKAGECONFIG[journald] = "-DENABLE_JOURNALD_LOG=ON,-DENABLE_JOURNALD_LOG=OFF,"
PACKAGECONFIG[lcms] = "-DUSE_LCMS=ON,-DUSE_LCMS=OFF,lcms"
PACKAGECONFIG[wpe-legacy-api] = "-DENABLE_WPE_LEGACY_API=ON,-DENABLE_WPE_LEGACY_API=OFF,libwpe virtual/wpebackend,"
PACKAGECONFIG[libbacktrace] = "-DUSE_LIBBACKTRACE=ON,-DUSE_LIBBACKTRACE=OFF,libbacktrace"
PACKAGECONFIG[minibrowser] = "-DENABLE_MINIBROWSER=ON,-DENABLE_MINIBROWSER=OFF,wayland-native wayland-protocols wpebackend-fdo"
PACKAGECONFIG[mediasource] = "-DENABLE_MEDIA_SOURCE=ON,-DENABLE_MEDIA_SOURCE=OFF,gstreamer1.0 gstreamer1.0-plugins-good"
PACKAGECONFIG[mediastream] = "-DENABLE_MEDIA_STREAM=ON,-DENABLE_MEDIA_STREAM=OFF,gstreamer1.0 gstreamer1.0-plugins-bad"
# pdfjs adds +3MB to the binary size of libWPEWebKit
PACKAGECONFIG[pdfjs] = "-DENABLE_PDFJS=ON,-DENABLE_PDFJS=OFF,"

PACKAGECONFIG[speech-synthesis] = "-DENABLE_SPEECH_SYNTHESIS=ON,-DENABLE_SPEECH_SYNTHESIS=OFF,flite"
PACKAGECONFIG[spellcheck] = "-DENABLE_SPELLCHECK=ON,-DENABLE_SPELLCHECK=OFF,enchant2"

PACKAGECONFIG[sysprof] = "-DUSE_SYSPROF_CAPTURE=ON, -DUSE_SYSPROF_CAPTURE=OFF,"
PACKAGECONFIG[system-sysprof] = "-DUSE_SYSTEM_SYSPROF_CAPTURE=ON, -DUSE_SYSTEM_SYSPROF_CAPTURE=OFF, sysprof"
PACKAGECONFIG[video] = "-DENABLE_VIDEO=ON,-DENABLE_VIDEO=OFF,gstreamer1.0 gstreamer1.0-plugins-base"
PACKAGECONFIG[webaudio] = "-DENABLE_WEB_AUDIO=ON,-DENABLE_WEB_AUDIO=OFF,gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good"
PACKAGECONFIG[webdriver] = "-DENABLE_WEBDRIVER=ON,-DENABLE_WEBDRIVER=OFF,"
PACKAGECONFIG[woff2] = "-DUSE_WOFF2=ON,-DUSE_WOFF2=OFF,woff2"
PACKAGECONFIG[remote-inspector] = "-DENABLE_REMOTE_INSPECTOR=ON,-DENABLE_REMOTE_INSPECTOR=OFF,"
PACKAGECONFIG[webrtc] = "-DENABLE_WEB_RTC=ON,-DENABLE_WEB_RTC=OFF,libvpx libevent libopus openh264"
# Requires wpe-platform: upstream has WEBKIT_OPTION_DEPEND(ENABLE_WPE_QT_API
# ENABLE_WPE_PLATFORM), and dropping wpe-platform silently disables the Qt API.
PACKAGECONFIG[qtwpe] = "-DENABLE_WPE_QT_API=ON ${CMAKE_QT_OECONF},-DENABLE_WPE_QT_API=OFF,qtbase-native qtbase qtdeclarative libepoxy wpebackend-fdo ${QT_BUILD_DEPS}"
PACKAGECONFIG[unified-builds] = "-DENABLE_UNIFIED_BUILDS=ON,-DENABLE_UNIFIED_BUILDS=OFF,"
PACKAGECONFIG[thunder] = "-DENABLE_THUNDER=ON,-DENABLE_THUNDER=OFF,virtual/open-cdm"
PACKAGECONFIG[webxr] = "-DENABLE_WEBXR=ON,-DENABLE_WEBXR=OFF,openxr"

# Build option for WPE API 1.1. It conflicts with wpe-platform, so drop
# wpe-platform from PACKAGECONFIG when enabling this one.
PACKAGECONFIG[wpe-1-1-api] = "-DENABLE_WPE_1_1_API:BOOL=ON,-DENABLE_WPE_1_1_API:BOOL=OFF,"

# Build options for the WPE platform API and its backends. All of them default
# to ON upstream; the backends are kept separate so their dependencies can be
# dropped on targets that do not need them.
PACKAGECONFIG[wpe-platform] = "-DENABLE_WPE_PLATFORM=ON,-DENABLE_WPE_PLATFORM=OFF,libxkbcommon"
PACKAGECONFIG[wpe-platform-drm] = "-DENABLE_WPE_PLATFORM_DRM=ON,-DENABLE_WPE_PLATFORM_DRM=OFF,libdrm libinput udev"
PACKAGECONFIG[wpe-platform-headless] = "-DENABLE_WPE_PLATFORM_HEADLESS=ON,-DENABLE_WPE_PLATFORM_HEADLESS=OFF,"
PACKAGECONFIG[wpe-platform-wayland] = "-DENABLE_WPE_PLATFORM_WAYLAND=ON,-DENABLE_WPE_PLATFORM_WAYLAND=OFF,wayland wayland-native wayland-protocols"

EXTRA_OECMAKE = " -DPORT=WPE -G Ninja"

# USE_VULKAN follows ENABLE_EXPERIMENTAL_FEATURES upstream, but it needs volk
# and no volk recipe is available in the supported layers.
EXTRA_OECMAKE:append = " -DUSE_VULKAN=OFF -DCMAKE_DISABLE_PRECOMPILE_HEADERS=ON"

# TODO: documentation and introspection are disabled by default because the are
# causing cross-compiling build errors
# PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'api-documentation', 'documentation', '' ,d)} introspection"

# If SSE code compiles, assume it runs successfully (it can't actually run
# because of cross compiling)
EXTRA_OECMAKE:append:x86 = " -DHAVE_SSE2_EXTENSIONS_EXITCODE=0"
# Javascript JIT is not supported on ppc/arm < v6/RISCV/mips64
PACKAGECONFIG:remove:powerpc = "jit"
PACKAGECONFIG:remove:powerpc64 = "jit"
PACKAGECONFIG:remove:powerpc64le = "jit"
PACKAGECONFIG:remove:armv4 = "jit"
PACKAGECONFIG:remove:armv5 = "jit"
PACKAGECONFIG:remove:armv6 = "jit"
PACKAGECONFIG:remove:riscv32 = "jit"
PACKAGECONFIG:remove:riscv64 = "jit"
PACKAGECONFIG:remove:mipsarchn64 = "jit"
PACKAGECONFIG:remove:mipsarchn32 = "jit"
PACKAGECONFIG:remove:loongarch64 = "jit"

# Javascript JIT is not supported on x86
PACKAGECONFIG:remove:x86 = "jit"

LDFLAGS:append:riscv64 = " -pthread"

FULL_OPTIMIZATION:remove = "-g"

LEAD_SONAME = "libWPEWebKit.so"

# USE_MIMALLOC is the default on armv7, mips and riscv64. bmalloc links the
# mimalloc objects directly (Source/bmalloc/CMakeLists.txt appends
# $<TARGET_OBJECTS:mimalloc-obj>), so the copy that the vendored mimalloc
# installs under ${libdir}/mimalloc-<version> is unused at runtime.
do_install:append() {
    rm -rf ${D}${libdir}/mimalloc-*
}

PACKAGES =+ "${PN}-web-inspector-plugin ${PN}-qtwpe-qml-plugin"
FILES:${PN} += "${libdir}/wpe-webkit*/injected-bundle/libWPEInjectedBundle.so"
FILES:${PN}-web-inspector-plugin += "${datadir}/wpe-webkit-*/inspector.gresource"
# nooelint: oelint.vars.insaneskip - ignored for convenience. We need to recheck if problem persist
INSANE_SKIP:${PN}-web-inspector-plugin = "dev-so"

# nooelint: oelint.vars.insaneskip - ignored for convenience. We need to recheck if problem persist
INSANE_SKIP:${PN}-qtwpe-qml-plugin = "dev-so"

# JSC JIT on ARMv7 is better supported with Thumb2 instruction set.
ARM_INSTRUCTION_SET:armv7a = "thumb"
ARM_INSTRUCTION_SET:armv7r = "thumb"
ARM_INSTRUCTION_SET:armv7m = "thumb"
ARM_INSTRUCTION_SET:armv7ve = "thumb"

# Extra runtime depends
# nooelint: oelint.vars.dependsordered - ignored for convenience
# Don't add "libgles2" here as it is box dependent
RDEPENDS:${PN} += "\
    ${@bb.utils.contains('PACKAGECONFIG', 'remote-inspector', '${PN}-web-inspector-plugin', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'gst_gl', 'gstreamer1.0-plugins-base-opengl', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'mediasource', 'gstreamer1.0-plugins-good-isomp4', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'webaudio', 'gstreamer1.0-plugins-good-wavparse', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'video', 'gstreamer1.0-plugins-base-app \
                                                    gstreamer1.0-plugins-base-audioconvert \
                                                    gstreamer1.0-plugins-base-audioresample \
                                                    gstreamer1.0-plugins-base-gio \
                                                    gstreamer1.0-plugins-base-playback \
                                                    gstreamer1.0-plugins-base-typefindfunctions \
                                                    gstreamer1.0-plugins-base-videoconvertscale \
                                                    gstreamer1.0-plugins-base-volume \
                                                    gstreamer1.0-plugins-good-audiofx \
                                                    gstreamer1.0-plugins-good-audioparsers \
                                                    gstreamer1.0-plugins-good-autodetect \
                                                    gstreamer1.0-plugins-good-avi \
                                                    gstreamer1.0-plugins-good-deinterlace \
                                                    gstreamer1.0-plugins-good-interleave \
                                                    ', '', d)} \
    libepoxy \
    libsoup \
    woff2 \
"

RDEPENDS:${PN}-web-inspector-plugin += "\
    shared-mime-info \
"

# Extra runtime recommends
RRECOMMENDS:${PN} += "\
    ca-certificates \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    ttf-dejavu-serif \
    ${PN}-qtwpe-qml-plugin \
    ${@bb.utils.contains('PACKAGECONFIG', 'video', 'gstreamer1.0-plugins-base-meta gstreamer1.0-plugins-good-meta gstreamer1.0-plugins-bad-meta', '', d)} \
"
