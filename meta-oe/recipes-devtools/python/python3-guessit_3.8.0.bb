SUMMARY = "GuessIt - a library for guessing information from video filenames."
HOMEPAGE = "https://doc.guessit.io/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb3ca60759f3202f1ae42e3519cd06bc"
DEPENDS += "${PYTHON_PN}-pytest-runner-native"

SRC_URI[md5sum] = "5a488b3a1fb0e8df0a5b9af8eb336209"
SRC_URI[sha256sum] = "6619fcbbf9a0510ec8c2c33744c4251cad0507b1d573d05c875de17edc5edbed"

inherit ${PYTHON_PN}-dir pypi setuptools3 

FILES:${PN}-src += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.c \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.c \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.c \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.c \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.c \
    ${PYTHON_SITEPACKAGES_DIR}/*.h \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.h \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.h \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.h \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.h \
    ${PYTHON_SITEPACKAGES_DIR}/*.exe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.exe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.exe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.exe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.exe \
    ${PYTHON_SITEPACKAGES_DIR}/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.py \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*.py \
    ${libdir}/${PYTHON_DIR}/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*/*/*.py \
    ${libdir}/enigma2/python/*.py \
    ${libdir}/enigma2/python/*/*.py \
    ${libdir}/enigma2/python/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*/*.py \
    ${libdir}/enigma2/python/*/*/*/*/*/*.py \
    "


FILES:${PN}-test += " \
    ${PYTHON_SITEPACKAGES_DIR}/test \
    ${PYTHON_SITEPACKAGES_DIR}/tests \
    ${PYTHON_SITEPACKAGES_DIR}/Test \
    ${PYTHON_SITEPACKAGES_DIR}/Tests \
    ${PYTHON_SITEPACKAGES_DIR}/_test \
    ${PYTHON_SITEPACKAGES_DIR}/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/*/tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/Test \
    ${PYTHON_SITEPACKAGES_DIR}/*/Tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/_test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/_test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/Test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/Tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/_test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/tests \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/Test \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/Tests \
"

# some txt files which should go into -doc
FILES:${PN}-doc += " \
    ${PYTHON_SITEPACKAGES_DIR}/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-info \
    ${PYTHON_SITEPACKAGES_DIR}/*-INFO \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-INFO \
    ${PYTHON_SITEPACKAGES_DIR}/*-safe \
    ${PYTHON_SITEPACKAGES_DIR}/*/*-safe \
    ${PYTHON_SITEPACKAGES_DIR}/doc \
    ${PYTHON_SITEPACKAGES_DIR}/*/doc \
    ${PYTHON_SITEPACKAGES_DIR}/LICENSE \
    ${PYTHON_SITEPACKAGES_DIR}/README \
    "

FILES:${PN}-dbg += " \
    ${PYTHON_SITEPACKAGES_DIR}/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*/*.debug \
    ${PYTHON_SITEPACKAGES_DIR}/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*.egg-info \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*/*.egg-info \
    "
