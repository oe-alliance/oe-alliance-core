PRINC = "2"

RPROVIDES += "python-pickle-native python-pprint-native"

BUILD_OPTIMIZATION := "${@oe_filter_out('-march=native', '${BUILD_OPTIMIZATION}', d)}"

