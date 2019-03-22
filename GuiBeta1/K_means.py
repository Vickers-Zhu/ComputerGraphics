import cv2
import numpy as np
import random
from math import *

class kmeans:
    def __init__(self, img):
        self.img = img

    def init(self, k):
        self.cluster = np.zeros((k, self.img.shape[0] * self.img.shape[1], 2), dtype=np.int)
        self.centroids = np.zeros((k, 3), dtype=np.int)
        self.counter = np.zeros(k, dtype=np.int)
        self.new_centroids = np.zeros((k, 3), dtype=np.int)


        for h in range(0, self.centroids.shape[0]):
            for w in range(0, self.centroids.shape[1]):
                self.centroids[h, w] = random.randint(0, 255)
        # print(self.centroids[1, :])

    def dist_Eclud(self, vec1, vec2):
        return sqrt(pow((vec1[0] - vec2[0]), 2) + pow((vec1[1] - vec2[1]), 2) + pow((vec1[2] - vec2[2]), 2))

    def min_dist_each(self, point):
        min = 100000000000.0
        index = -1
        for i in range(0, self.centroids.shape[0]):
            dist = self.dist_Eclud(point, self.centroids[i, :])
            if dist < min:
                min = dist
                index = i
        return index

    def whole_img(self):
        for h in range(0, self.img.shape[0]):
            for w in range(0, self.img.shape[1]):
                i = self.min_dist_each(self.img[h, w, :])
                self.cluster[i, self.counter[i], 0] = h
                self.cluster[i, self.counter[i], 1] = w
                self.counter[i] = self.counter[i] + 1
        # print(self.counter)

    def avr(self):
        for k in range(0, len(self.counter)):
            for i in range(0, self.counter[k]):
                if self.counter[k] == 0:
                    continue
                self.new_centroids[k, 0] += self.img[self.cluster[k, i, 0], self.cluster[k, i, 1], 0]
                self.new_centroids[k, 1] += self.img[self.cluster[k, i, 0], self.cluster[k, i, 1], 1]
                self.new_centroids[k, 2] += self.img[self.cluster[k, i, 0], self.cluster[k, i, 1], 2]

        for k in range(0, len(self.counter)):
            if self.counter[k] == 0:
                continue
            self.new_centroids[k, 0] = int(self.new_centroids[k, 0] / self.counter[k])
            self.new_centroids[k, 1] = int(self.new_centroids[k, 1] / self.counter[k])
            self.new_centroids[k, 2] = int(self.new_centroids[k, 2] / self.counter[k])

    def is_over(self):
        over = True
        for k in range(0, len(self.counter)):
            if not(self.new_centroids[k, 0] == self.centroids[k, 0] and self.new_centroids[k, 1] == self.centroids[k, 1]
                   and self.new_centroids[k, 2] == self.centroids[k, 2]):
                over = False
        return over

    def process(self, k):
        self.init(k)
        while True:
            self.whole_img()
            self.avr()
            if self.is_over():
                break
            self.centroids = self.new_centroids
            self.new_centroids = np.zeros((k, 3), dtype=np.int)
            self.counter = np.zeros(k, dtype=np.int)
            self.cluster = np.zeros((k, self.img.shape[0] * self.img.shape[1], 2), dtype=np.int)
        # self.test()





