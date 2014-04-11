DEPENDS := "${@oe_filter_out('^(mysql5|postgresql|sqlite)$', '${DEPENDS}', d)}"

SRC_URI += "file://0001-Qt-build-NPAPI-without-X11.patch \
            file://0002-Qt-Add-HbbTv-MIME-types.patch \
            file://0003-Qt-expose-WebKits-WebSecurityEnabled-setting.patch \
            file://0004-Qt-add-Q_GUI_EXPORT-to-QUpdateLaterEvent.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

QT_CONFIG_FLAGS += "-nomake demos -nomake docs -nomake examples"

QT_GLIB_FLAGS = "-no-glib"
QT_IMAGEFORMAT_FLAGS += "-no-libmng"
QT_PHONON_FLAGS = "-no-phonon"
QT_QDBUS_FLAGS = "-no-qdbus"
QT_QT3SUPPORT_FLAGS = "-no-qt3support"
QT_SQL_DRIVER_FLAGS = "-no-sql-ibase -no-sql-mysql -no-sql-odbc -no-sql-psql -no-sql-sqlite2 -plugin-sql-sqlite -system-sqlite"
QT_WEBKIT_FLAGS = "-webkit"

QT_DECORATION_FLAGS = "-plugin-decoration-default -plugin-decoration-styled -plugin-decoration-windows"
QT_GFX_DRIVER_FLAGS = "-plugin-gfx-directfb -plugin-gfx-linuxfb -no-gfx-multiscreen -no-gfx-qvfb -no-gfx-transformed -no-gfx-vnc"
QT_KBD_DRIVER_FLAGS = "-plugin-kbd-linuxinput -no-kbd-tty -no-kbd-qvfb"
QT_MOUSE_DRIVER_FLAGS = "-qt-mouse-linuxinput -plugin-mouse-linuxtp -plugin-mouse-pc -no-mouse-qvfb -plugin-mouse-tslib"
