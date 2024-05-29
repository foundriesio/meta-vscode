DESCRIPTION = "Packaging for prebuilt versions of vscode"
HOMEPAGE = "https://code.visualstudio.com/download"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../VSCode-linux-${VSCODE_ARCH}/LICENSES.chromium.html;md5=c590e5227ea914c9a4187d5b1cbefe93"

COMPATIBLE_HOST = "(x86_64|aarch64|arm).*-linux"

inherit bin_package

def get_host_arch(d):
    arch = d.getVar('HOST_ARCH')
    if arch == "aarch64":
        return "arm64"
    elif arch == "arm":
        return "armhf"
    elif arch == "x86_64":
        return "x64"
    bb.error("unrecognized HOST_ARCH: %s", arch)

VSCODE_ARCH = "${@get_host_arch(d)}"

GIT_SHA = "dc96b837cf6bb4af9cd736aa3af08cf8279f7685"
TIMESTAMP-arm64 = "1715058820"
TIMESTAMP-armhf = "1715058860"
TIMESTAMP-x64 = "1715058993"
SRC_URI = "https://vscode.download.prss.microsoft.com/dbazure/download/stable/${GIT_SHA}/code-stable-${VSCODE_ARCH}-${TIMESTAMP-${VSCODE_ARCH}}.tar.gz;name=vscode-${VSCODE_ARCH}"
SRC_URI[vscode-arm64.md5sum] = "c71bd32ab70f5615621e61c548be9e4c"
SRC_URI[vscode-armhf.md5sum] = "68e224b57fa70999002819aa5869ed91"
SRC_URI[vscode-x64.md5sum] = "d36230c467c3a4f929881f3a61756971"

do_install() {
    install -d ${D}${datadir}/vscode
    cp -r ${WORKDIR}/VSCode-linux-${VSCODE_ARCH}/* ${D}${datadir}/vscode
}

FILES:${PN} = "${datadir}"
INSANE_SKIP:${PN} += "already-stripped file-rdeps ldflags libdir"
INSANE_SKIP:${PN}-dbg += "libdir"
