FILESEXTRAPATHS:prepend := "${THISDIR}/gcc-14.2:"

SRC_URI:append = " file://0005-optional-libstdc.patch \
                    file://0001-Revert-Arm-Block-predication-on-atomics-PR111235.patch"
