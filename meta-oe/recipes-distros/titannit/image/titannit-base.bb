SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r16"

inherit packagegroup

DEPENDS = "\
    curl \
	titannit-bootlogo \
    freetype \
    libpng \
    jpeg \
    rtmpdump \
    gstreamer \
    gst-plugins-base \
    gst-plugins-good \
    gst-plugins-bad \
    gst-plugins-ugly \
    gst-plugin-subsink \
    titan-gmediarender \
    glib-networking \
    ethtool \
    ${@base_contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
    libdreamdvd \
    openssl \
    tuxtxt-enigma2 \
    ${@base_contains("TARGET_ARCH", "sh4", "libmmeimage " , "", d)} \
    ethtool \
    rtmpdump \
    packagegroup-base-smbfs-client \
    ${@base_contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \	
    bash \
    libcrypto-compat-0.9.7 \
    libcrypto-compat-0.9.8 \
    zip \
    procps \
    ntfs-3g \
    hddtemp \
    rtmpdump \
    minidlna \
    titan-base \
    "    