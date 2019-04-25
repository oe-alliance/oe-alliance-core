FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
            file://0001-Fixes-for-sort_keys-bug-and-a-typo.patch \
            file://0002-added-static-to-C-functions-where-possible.patch \
            file://0003-Fix-for-overflowing-long-causing-invalid-json.patch \
            file://0004-Following-std-json-handling-of-None-dict-key.patch \
            file://0005-Fix-for-incorrect-order-when-using-OrderedDict.patch \
            file://0006-Fix-test-deprication-warning.patch \
            file://0007-Update-setup.py-and-readme.patch \
"

include python-package-split.inc
