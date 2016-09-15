DESCRIPTION = "Provides userspace xenomai support and libraries needed to for \
real-time applications using the xenomai RTOS implementation (v3)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://include/COPYING;md5=79ed705ccb9481bf9e7026b99f4e2b0e"
SECTION = "xenomai"
HOMEPAGE = "http://www.xenomai.org/"
PR = "r0"

SRC_URI = "http://xenomai.org/downloads/xenomai/stable/xenomai-3.0.2.tar.bz2"

S = "${WORKDIR}/xenomai-${PV}"

inherit autotools pkgconfig

includedir = "/usr/include/xenomai"

EXTRA_OECONF_append_mx6 = "--enable-smp"

#Fixes QA Issues: non debug package contains .debug directory
FILES_${PN}-dbg += "/usr/bin/regression/posix/.debug"
FILES_${PN}-dbg += "/usr/bin/regression/native/.debug"
FILES_${PN}-dbg += "/usr/bin/regression/native+posix/.debug"
FILES_${PN}-dbg += "/usr/demo/.debug/*"

# Fixes QA Error - Non -dev package contains symlink .so
FILES_${PN}-dev += "/usr/lib/*.se"

#Add directories to package for shipping
FILES_${PN} += "/dev/*"
FILES_${PN} += "/usr/bin/*"
FILES_${PN} += "/usr/lib/*"
FILES_${PN} += "/usr/sbin/*"
FILES_${PN} += "/usr/include/*"
FILES_${PN} += "/usr/lib/*"
FILES_${PN} += "/usr/share/doc/*"
FILES_${PN} += "/usr/share/man/*"
FILES_${PN} += "/usr/demo/*"

SRC_URI[md5sum] = "bf0986db38f02174b2cd55ec6ed2f90b"
SRC_URI[sha256sum] = "731fbf720c4e6c31fe00a668bb9b2659a5dce06ee8ba684c14f8b2da8d4957bf"
