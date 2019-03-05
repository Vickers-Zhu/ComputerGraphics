from PyQt5 import QtWidgets, QtGui, QtCore
from CG import Ui_MainWindow
from input import Ui_Modifier
import CG_Input
from PIL import Image
import cv2
import os
import numpy as np


class mywindow(QtWidgets.QMainWindow):
    def __init__(self, parent=None):
        super(mywindow, self).__init__(parent=parent)
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)
        self.ui.pushButton_2.clicked.connect(self.on_btn_import_clicked)
        self.ui.pushButton.clicked.connect(self.on_btn_execute_clicked)
        self.ui.pushButton_3.clicked.connect(self.show_input_widget)
        self.input = None
        self.core = 0.0
        self.offset = 0.0
        self.divisor = 1.0

    def show_input_widget(self):
        self.input = CG_Input.myinput()
        self.input.show()

    def on_btn_import_clicked(self):
        filename = QtWidgets.QFileDialog.getOpenFileName(self, "OpenFile", ".", "Image Files(*.jpg *.jpeg *.png)")[0]
        if len(filename):
            self.img = cv2.imread(filename)
            self.img = cv2.cvtColor(self.img, cv2.COLOR_BGR2RGB)  # Change channel
            x = self.img.shape[1]  # Get the size of the image
            y = self.img.shape[0]
            zoom = 0.5  # zoom of the image
            frame = QtGui.QImage(self.img, x, y, QtGui.QImage.Format_RGB888)
            pix = QtGui.QPixmap.fromImage(frame)
            item = QtWidgets.QGraphicsPixmapItem(pix)  # make the pix map
            item.setScale(zoom)
            scene = QtWidgets.QGraphicsScene()  # to fit the scene of the UI
            scene.addItem(item)
            self.ui.graphicsView.setScene(scene)

    def on_btn_execute_clicked(self):
        if os.path.isfile("AfterProcessed.jpg"):
            os.remove("AfterProcessed.jpg")

        self.ui.graphicsView_2.setScene(None)
        self.TestInput()
        self.RadioGroup()

        if os.path.isfile("AfterProcessed.jpg"):
            image2 = cv2.imread("AfterProcessed.jpg")
            image2 = cv2.cvtColor(image2, cv2.COLOR_BGR2RGB)
            x = image2.shape[1]
            y = image2.shape[0]
            zoom = 0.5  # zoom of the image
            frame = QtGui.QImage(image2, x, y, QtGui.QImage.Format_RGB888)
            pix = QtGui.QPixmap.fromImage(frame)
            item = QtWidgets.QGraphicsPixmapItem(pix)
            item.setScale(zoom)
            scene = QtWidgets.QGraphicsScene()
            scene.addItem(item)
            self.ui.graphicsView_2.setScene(scene)

    def RadioGroup(self):
        if self.ui.radioButton_9.isChecked():
            self.Inverse()
        if self.ui.radioButton_7.isChecked():
            self.Brightness(1.5)
        if self.ui.radioButton_8.isChecked():
            self.Contrast(1.5)
        if self.ui.radioButton_3.isChecked():
            self.Customized("gaussian_3")
        if self.ui.radioButton_2.isChecked():
            self.Customized("blur")
        if self.ui.radioButton_5.isChecked():
            self.Customized("sharpen")
        if self.ui.radioButton_6.isChecked():
            self.Customized("edge_1")
        if self.ui.radioButton.isChecked():
            self.Customized("emboss_1")

    def TestInput(self):
        if bool(self.input):
            c11 = float(self.input.ui.lineEdit.text())
            c12 = float(self.input.ui.lineEdit_2.text())
            c13 = float(self.input.ui.lineEdit_3.text())
            c14 = float(self.input.ui.lineEdit_4.text())
            c15 = float(self.input.ui.lineEdit_5.text())
            c16 = float(self.input.ui.lineEdit_6.text())
            c17 = float(self.input.ui.lineEdit_7.text())
            c18 = float(self.input.ui.lineEdit_8.text())
            c19 = float(self.input.ui.lineEdit_9.text())
            c21 = float(self.input.ui.lineEdit_10.text())
            c22 = float(self.input.ui.lineEdit_11.text())
            c23 = float(self.input.ui.lineEdit_12.text())
            c24 = float(self.input.ui.lineEdit_13.text())
            c25 = float(self.input.ui.lineEdit_14.text())
            c26 = float(self.input.ui.lineEdit_15.text())
            c27 = float(self.input.ui.lineEdit_16.text())
            c28 = float(self.input.ui.lineEdit_17.text())
            c29 = float(self.input.ui.lineEdit_18.text())
            c31 = float(self.input.ui.lineEdit_19.text())
            c32 = float(self.input.ui.lineEdit_20.text())
            c33 = float(self.input.ui.lineEdit_21.text())
            c34 = float(self.input.ui.lineEdit_22.text())
            c35 = float(self.input.ui.lineEdit_23.text())
            c36 = float(self.input.ui.lineEdit_24.text())
            c37 = float(self.input.ui.lineEdit_25.text())
            c38 = float(self.input.ui.lineEdit_26.text())
            c39 = float(self.input.ui.lineEdit_27.text())
            c41 = float(self.input.ui.lineEdit_28.text())
            c42 = float(self.input.ui.lineEdit_29.text())
            c43 = float(self.input.ui.lineEdit_30.text())
            c44 = float(self.input.ui.lineEdit_31.text())
            c45 = float(self.input.ui.lineEdit_32.text())
            c46 = float(self.input.ui.lineEdit_33.text())
            c47 = float(self.input.ui.lineEdit_34.text())
            c48 = float(self.input.ui.lineEdit_35.text())
            c49 = float(self.input.ui.lineEdit_36.text())
            c51 = float(self.input.ui.lineEdit_37.text())
            c52 = float(self.input.ui.lineEdit_38.text())
            c53 = float(self.input.ui.lineEdit_39.text())
            c54 = float(self.input.ui.lineEdit_40.text())
            c55 = float(self.input.ui.lineEdit_41.text())
            c56 = float(self.input.ui.lineEdit_42.text())
            c57 = float(self.input.ui.lineEdit_43.text())
            c58 = float(self.input.ui.lineEdit_44.text())
            c59 = float(self.input.ui.lineEdit_45.text())
            c61 = float(self.input.ui.lineEdit_46.text())
            c62 = float(self.input.ui.lineEdit_47.text())
            c63 = float(self.input.ui.lineEdit_48.text())
            c64 = float(self.input.ui.lineEdit_49.text())
            c65 = float(self.input.ui.lineEdit_50.text())
            c66 = float(self.input.ui.lineEdit_51.text())
            c67 = float(self.input.ui.lineEdit_52.text())
            c68 = float(self.input.ui.lineEdit_53.text())
            c69 = float(self.input.ui.lineEdit_54.text())
            c71 = float(self.input.ui.lineEdit_55.text())
            c72 = float(self.input.ui.lineEdit_56.text())
            c73 = float(self.input.ui.lineEdit_57.text())
            c74 = float(self.input.ui.lineEdit_58.text())
            c75 = float(self.input.ui.lineEdit_59.text())
            c76 = float(self.input.ui.lineEdit_60.text())
            c77 = float(self.input.ui.lineEdit_61.text())
            c78 = float(self.input.ui.lineEdit_62.text())
            c79 = float(self.input.ui.lineEdit_63.text())
            c81 = float(self.input.ui.lineEdit_64.text())
            c82 = float(self.input.ui.lineEdit_65.text())
            c83 = float(self.input.ui.lineEdit_66.text())
            c84 = float(self.input.ui.lineEdit_67.text())
            c85 = float(self.input.ui.lineEdit_68.text())
            c86 = float(self.input.ui.lineEdit_69.text())
            c87 = float(self.input.ui.lineEdit_70.text())
            c88 = float(self.input.ui.lineEdit_71.text())
            c89 = float(self.input.ui.lineEdit_72.text())
            c91 = float(self.input.ui.lineEdit_73.text())
            c92 = float(self.input.ui.lineEdit_74.text())
            c93 = float(self.input.ui.lineEdit_75.text())
            c94 = float(self.input.ui.lineEdit_76.text())
            c95 = float(self.input.ui.lineEdit_77.text())
            c96 = float(self.input.ui.lineEdit_78.text())
            c97 = float(self.input.ui.lineEdit_79.text())
            c98 = float(self.input.ui.lineEdit_80.text())
            c99 = float(self.input.ui.lineEdit_81.text())
        self.core = np.array(([c11,c12,c13,c14,c15,c16,c17,c18,c19],
                              [c21,c22,c23,c24,c25,c26,c27,c28,c29],
                              [c31,c32,c33,c34,c35,c36,c37,c38,c39],
                              [c41,c42,c43,c44,c45,c46,c47,c48,c49],
                              [c51,c52,c53,c54,c55,c56,c57,c58,c59],
                              [c61,c62,c63,c64,c65,c66,c67,c68,c69],
                              [c71,c72,c73,c74,c75,c76,c77,c78,c79],
                              [c81,c82,c83,c84,c85,c86,c87,c88,c89],
                              [c91,c92,c93,c94,c95,c96,c97,c98,c99]))
        if bool(self.ui.lineEdit.text()):
            self.offset = float(self.ui.lineEdit.text())
        if bool(self.ui.lineEdit_2.text()):
            self.divisor = float(self.ui.lineEdit_2.text())

    def IsRange(self, arg):
        return max(0, min(255, arg))

    def Customized(self, args):
        border = 10
        gaussian_3 = np.array(([0, 1, 0], [1, 4, 1], [0, 1, 0])) * 1/8
        blur = np.array(([1, 1, 1], [1, 1, 1], [1, 1, 1])) * 1/9
        sharpen = np.array(([0, -1, 0], [-1, 5, -1], [0, -1, 0]))
        edge_1 = np.array(([0, -1, 0], [0, 1, 0], [0, 0, 0]))
        emboss_1 = np.array(([-1, -1, -1], [0, 1, 0], [1, 1, 1]))
        if self.core is not 0.0:
            args = "self.core"

        img = self.img.copy()
        B = self.Conv(img[:, :, 0], eval(args), border)
        G = self.Conv(img[:, :, 1], eval(args), border)
        R = self.Conv(img[:, :, 2], eval(args), border)
        img = np.stack((R, G, B), axis=2)
        Image.fromarray(img)
        cv2.imwrite("AfterProcessed.jpg", img)

    def Inverse(self):
        img = self.img.copy()
        for i in range(0, self.img.shape[0]):
            for j in range(0, self.img.shape[1]):
                img[i, j] = 255 - self.img[i, j]
        cv2.imwrite("AfterProcessed.jpg", img)

    def Brightness(self, arg):
        img = self.img.copy()
        for w in range(0, img.shape[1]):
            for h in range(0, img.shape[0]):
                img[h, w, 0] = self.IsRange(int(img[h, w, 0] * arg))
                img[h, w, 1] = self.IsRange(int(img[h, w, 1] * arg))
                img[h, w, 2] = self.IsRange(int(img[h, w, 2] * arg))
        cv2.imwrite("AfterProcessed.jpg", img)

    def Contrast(self, arg):
        img = self.img.copy()
        for w in range(0, img.shape[1]):
            for h in range(0, img.shape[0]):
                img[h, w, 0] = self.IsRange(int((img[h, w, 0] - 127) * arg + 127))
                img[h, w, 1] = self.IsRange(int((img[h, w, 0] - 127) * arg + 127))
                img[h, w, 2] = self.IsRange(int((img[h, w, 0] - 127) * arg + 127))
        cv2.imwrite("AfterProcessed.jpg", img)

    def Expand(self, img, arg):
        img = cv2.copyMakeBorder(img, arg, arg, arg, arg, cv2.BORDER_REPLICATE)
        return img

    def Conv(self, img, weight, bd):
        height, width = img.shape
        img = self.Expand(img, bd)
        h, w = weight.shape
        ver = int((h-1)/2)
        hor = int((h-1)/2)
        new_img = np.zeros((height, width), dtype=float)
        for j in range(0, width):
            for i in range(0, height):
                new_img[i, j] = self.IsRange(np.sum(img[i+bd-hor: i+bd+hor+1,
                                                    j+bd-ver: j+bd+ver+1] * weight) / self.divisor + self.offset)
        new_img = np.rint(new_img).astype('uint8')
        return new_img


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    myshow = mywindow()
    myshow.show()
    sys.exit(app.exec_())
