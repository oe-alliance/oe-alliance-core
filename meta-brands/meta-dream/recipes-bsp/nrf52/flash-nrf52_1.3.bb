SUMMARY = "Flasher for the DM900/DM920 NRF52 Bluetooth remote control adapter"

# DreamOS OE 2.5 ships the dongle firmware inside this ARM flasher.
# Keep the OE 2.5 flasher separate from the One/Two frontprocessor flasher.
SRC_URI[cortexa15hf-neon-vfpv4.md5sum] = "7065290264be1a543e1e668c3b066e06"
SRC_URI[cortexa15hf-neon-vfpv4.sha256sum] = "2b45b782105e856c45f2bf58b723ad3b73cabae1d99e4287967521abab1bba86"

COMPATIBLE_MACHINE = "^(dm900|dm920)$"

PRECOMPILED_URI = "https://source.mynonpublic.com/dreambox/spiegel/dreamlegacy.drecomx.net/opendreambox/2.5.0/flash-nrf52/${PV}/${PRECOMPILED_ARCH}/7065290264be1a543e1e668c3b066e06/flash-nrf52_${PV}_${PRECOMPILED_ARCH}.tar.xz;name=${PRECOMPILED_ARCH}"

inherit opendreambox-precompiled-binary4

INSANE_SKIP:${PN} += "ldflags"
