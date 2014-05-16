# Cross compiler badness causes BUILD params to be passed to the mipsel compiler
# I assume the recipe has a bloody good reason to do that, so just remove the
# march=native directive from it so that the builds don't fail.
BUILD_CFLAGS := "${@bb.data.getVar('BUILD_CFLAGS',d,1).replace('-march=native', '')}"
BUILD_CXXFLAGS := "${@bb.data.getVar('BUILD_CXXFLAGS',d,1).replace('-march=native', '')}"
BUILD_CPPFLAGS := "${@bb.data.getVar('BUILD_CPPFLAGS',d,1).replace('-march=native', '')}"

