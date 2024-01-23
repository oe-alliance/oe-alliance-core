inherit ${PYTHON_PN}-dir

RDEPENDS:{PN}-src = "${PN}"

SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/tests/__init__.py ${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/tests/__init__.pyc"

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
    ${PYTHON_SITEPACKAGES_DIR}/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/*/*/*/*/*/*/*/*.txt \
    ${PYTHON_SITEPACKAGES_DIR}/doc \
    ${PYTHON_SITEPACKAGES_DIR}/*/doc \
    ${PYTHON_SITEPACKAGES_DIR}/LICENSE \
    ${PYTHON_SITEPACKAGES_DIR}/README \
    ${PYTHON_SITEPACKAGES_DIR}/*/*.misc \
    ${PYTHON_SITEPACKAGES_DIR}/*/*/.gitignore \
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