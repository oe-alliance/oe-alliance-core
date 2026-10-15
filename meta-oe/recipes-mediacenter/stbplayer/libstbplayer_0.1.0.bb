SUMMARY = "Stable hardware-media backend ABI for STB Kodi"
DESCRIPTION = "A small C ABI, loader and probe tool for machine-specific STB video backends and DVB hardware audio output."
HOMEPAGE = "https://github.com/oe-alliance"

PR = "r74"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45071750435f2d50d503492ef8e003db"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/oe-alliance/stb-kodi-libstbplayer.git;protocol=https;branch=main"

inherit cmake pkgconfig

# The backend is selected and configured from MACHINE/SOC_FAMILY, including
# codec capabilities and platform-specific ABI variants.  Do not let builds
# sharing the same CPU tune overwrite each other in a public package feed.
PACKAGE_ARCH = "${MACHINE}"

# HiSilicon layers use several feature spellings.  SOC_FAMILY is consistent
# across all Kodi-capable HiSi receivers and avoids maintaining model lists.
HISI_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('hisi') else '0'}"
BCM_DVB_STB = "${@'1' if (d.getVar('SOC_FAMILY') or '').startswith('bcm') and d.getVar('TARGET_ARCH') in ('arm', 'mipsel') else '0'}"
VUPLUS_MIPSEL_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') == 'mipsel' else '0'}"
VUPLUS_ARM_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('TARGET_ARCH') == 'arm' and d.getVar('MACHINE') != 'vuduo4klite' else '0'}"
# Official Vu+ DVB drivers own their decoder STC channel but expose neither
# platform_get_stc_channel nor video_primer_get_stc_channel. Opening channel
# zero again therefore fails with ENODEV on both ARM and MIPSel generations.
# The community Duo 4K Lite uses the GigaBlue platform driver and is excluded.
VUPLUS_STC_FALLBACK_STB = "${@'1' if d.getVar('BRAND_OEM') == 'vuplus' and d.getVar('MACHINE') != 'vuduo4klite' else '0'}"
DREAM_BCM_STB = "${@'1' if (d.getVar('MACHINE') or '').startswith('dm') and d.getVar('BCM_DVB_STB') == '1' else '0'}"
DREAM_AMLOGIC_STB = "${@'1' if d.getVar('MACHINE') in ('dreamone', 'dreamtwo') and d.getVar('SOC_FAMILY') == 'meson64' else '0'}"
BCM_DVB_VARIANT = "${@'dreambox' if (d.getVar('MACHINE') or '').startswith('dm') else ('gigablue' if (d.getVar('MACHINE') or '') in ('gb7252', 'gb72604', 'vuduo4klite') else ('vuplus' if (d.getVar('MACHINE') or '').startswith('vu') else ('type2' if (d.getVar('MACHINE') or '') in ('xc7362', 'xc7346') else 'normal')))}"
BCM_DVB_CONFIG = "${@d.getVar('DVBMEDIASINK_CONFIG') or ''}"
DVB_AUDIO_VARIANT = "${@'dreambox' if (d.getVar('MACHINE') or '').startswith('dm') else ('dags' if '--with-dags' in (d.getVar('DVBMEDIASINK_CONFIG') or '').split() else 'normal')}"
# These receivers always use E2's Linux-DVB/PES video driver path.
HISI_LINUX_DVB_MACHINES = "hd60 hd61 hd66se pulse4k pulse4kmini multibox multiboxpro multiboxse h8se h9 h9combo h9combose h9se h10 h11 i55plus i55se"
# The original DVB-driver family can retain stale AUDIO_GET_PTS after seek.
# Keep the SF8008/libreader and other SDK families on their confirmed audio clock.
HISI_DVB_AUDIO_VIDEO_CLOCK = "${@'1' if d.getVar('MACHINE') in d.getVar('HISI_LINUX_DVB_MACHINES').split() else '0'}"
# This family needs a native HiSi PCM track; its DVB RAW-PCM path stalls.
# Compressed audio keeps the tested DVB decoder and media-clock feedback.
HISI_DVB_AUDIO_PCM_TRACK = "${HISI_DVB_AUDIO_VIDEO_CLOCK}"
# With native audio, libreader must own both decoders and their shared clock.
# gbmv200 covers gbip4k, gbtrio4k and gbtrio4kpro; viper4k is not Dinobot u571.
# DAGS app_init and Dinobot showiframe are different service/SDK families.
HISI_LIBREADER_MACHINES = "beyonwizv2 gbmv200 ip8 og2ott4k og2s4k sf8008 sf8008m sx88v2 sx988 ustym4kottpremium ustym4kpro ustym4ks2ottx viper4k"
HISI_USE_LINUX_DVB = "${@'1' if d.getVar('MACHINE') in d.getVar('HISI_LINUX_DVB_MACHINES').split() or d.getVar('MACHINE') in d.getVar('HISI_LIBREADER_MACHINES').split() else '0'}"

PACKAGECONFIG ??= "${@'hisi-dvb' if d.getVar('HISI_STB') == '1' else ('bcm-dvb' if d.getVar('BCM_DVB_STB') == '1' else ('dream-aml' if d.getVar('DREAM_AMLOGIC_STB') == '1' else ''))}"
PACKAGECONFIG[hisi-dvb] = "-DSTBP_BUILD_HISI_DVB_BACKEND=ON,-DSTBP_BUILD_HISI_DVB_BACKEND=OFF"
PACKAGECONFIG[bcm-dvb] = "-DSTBP_BUILD_BCM_DVB_BACKEND=ON,-DSTBP_BUILD_BCM_DVB_BACKEND=OFF"
PACKAGECONFIG[dream-aml] = "-DSTBP_BUILD_DREAM_AML_BACKEND=ON,-DSTBP_BUILD_DREAM_AML_BACKEND=OFF"

EXTRA_OECMAKE = " \
    -DSTBP_BUILD_DVB_AUDIO=ON \
    -DSTBP_DVB_AUDIO_VARIANT=${DVB_AUDIO_VARIANT} \
    -DSTBP_DVB_AUDIO_HAVE_EAC3=${@'ON' if '--with-eac3' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_DVB_AUDIO_HAVE_DTS=${@'ON' if '--with-dts' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_DVB_AUDIO_PCM_SAMPLE_CLOCK=${@'ON' if d.getVar('MACHINE') == 'vuduo4klite' else 'OFF'} \
    -DSTBP_DVB_AUDIO_HISI_VIDEO_CLOCK=${@'ON' if d.getVar('HISI_DVB_AUDIO_VIDEO_CLOCK') == '1' else 'OFF'} \
    -DSTBP_DVB_AUDIO_HISI_PCM_TRACK=${@'ON' if d.getVar('HISI_DVB_AUDIO_PCM_TRACK') == '1' else 'OFF'} \
    -DSTBP_DEFAULT_BACKEND_DIR=${libdir}/stbplayer \
    -DSTBP_HISI_MV310=${@'ON' if d.getVar('SOC_FAMILY') == 'hisi3798mv310' else 'OFF'} \
    -DSTBP_HISI_LINUX_DVB=${@'ON' if d.getVar('HISI_USE_LINUX_DVB') == '1' else 'OFF'} \
    -DSTBP_BCM_DVB_VARIANT=${BCM_DVB_VARIANT} \
    -DSTBP_BCM_DVB_HAVE_HEVC=${@'ON' if '--with-h265' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_WMV=${@'ON' if '--with-wmv' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP6=${@'ON' if '--with-vb6' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP8=${@'ON' if '--with-vb8' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_VP9=${@'ON' if '--with-vb9' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_HAVE_SPARK=${@'ON' if '--with-spark' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_LIMITED_MPEG4V2=${@'ON' if '--with-limited-mpeg4v2' in d.getVar('BCM_DVB_CONFIG').split() else 'OFF'} \
    -DSTBP_BCM_DVB_NEXUS_STC=${@'ON' if d.getVar('BCM_DVB_STB') == '1' and d.getVar('VUPLUS_STC_FALLBACK_STB') != '1' and d.getVar('DREAM_BCM_STB') != '1' else 'OFF'} \
    -DSTBP_BCM_DVB_STARTUP_CATCHUP=${@'ON' if d.getVar('BCM_DVB_VARIANT') == 'normal' or d.getVar('VUPLUS_ARM_STB') == '1' else 'OFF'} \
"

PACKAGES += "${PN}-backend-hisi-dvb ${PN}-backend-bcm-dvb ${PN}-backend-dream-aml"

FILES:${PN}-dev += "${includedir}/stbplayer"
FILES:${PN}-backend-hisi-dvb = "${libdir}/stbplayer/libstbplayer-backend-hisi-dvb.so"
RDEPENDS:${PN}-backend-hisi-dvb = "${PN}${@' (= ' + d.getVar('EXTENDPKGV') + ')' if d.getVar('HISI_DVB_AUDIO_VIDEO_CLOCK') == '1' or d.getVar('HISI_DVB_AUDIO_PCM_TRACK') == '1' else ''}"
FILES:${PN}-backend-bcm-dvb = "${libdir}/stbplayer/libstbplayer-backend-bcm-dvb.so"
# Official Vu+ DVB drivers own the only full Nexus STC channel and do not
# export an accessor for their decoder-associated SimpleStcChannel. The bridge
# can therefore load but cannot drive that channel (writes return ENODEV).
# These receivers use the proven decoder-PTS fallback instead.
# Dreambox BCM kernels expose neither that decoder accessor nor the full Nexus
# STC symbols, so they use the same fallback without installing a dead module.
RDEPENDS:${PN}-backend-bcm-dvb = "${PN}${@' stb-stc-host' if d.getVar('BCM_DVB_STB') == '1' and d.getVar('VUPLUS_STC_FALLBACK_STB') != '1' and d.getVar('DREAM_BCM_STB') != '1' else ''}"
FILES:${PN}-backend-dream-aml = "${libdir}/stbplayer/libstbplayer-backend-dream-aml.so"
RDEPENDS:${PN}-backend-dream-aml = "${PN}"
