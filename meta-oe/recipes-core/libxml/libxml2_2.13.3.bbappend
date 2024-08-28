FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://0001-Revert-dict-Get-random-seed-from-system-PRNG.patch"
