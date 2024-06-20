import { gsap } from "gsap";
import { ExpoScaleEase, RoughEase, SlowMo } from "gsap/EasePack";
import { CSSRulePlugin } from "gsap/CSSRulePlugin";
import { ScrollToPlugin } from "gsap/ScrollToPlugin";
gsap.registerPlugin(
  CSSRulePlugin,
  ScrollToPlugin,
  ExpoScaleEase,
  RoughEase,
  SlowMo
);

export const fly = function (node, animation) {
  gsap.fromTo(
    node,
    {
      autoAlpha: 0,
      x: animation.x || 0,
      y: animation.y || 0,
    },
    {
      autoAlpha: 1,
      x: 0,
      y: 0,
      duration: 1,
      delay: animation.delay || 0,
      ease: "bounce",
    }
  );
};

export const buttonIn = function (node, delay) {
  gsap.fromTo(
    node,
    {
      autoAlpha: 0,
      scale: 0,
    },
    {
      autoAlpha: 1,
      scale: 1,
      duration: 0.5,
      delay: delay || 0,
      ease: "elastic",
    }
  );
};

export const buttonOver = function (node, delay) {
  gsap.to(node, {
    scale: 1.15,
    duration: 0.5,
    ease: "elastic",
  });
};

export const buttonOut = function (node, delay) {
  gsap.to(node, {
    scale: 1,
    duration: 0.5,
    ease: "elastic",
  });
};

export const buttonDown = function (node, delay) {
  gsap.to(node, {
    scale: 0.95,
    duration: 0.5,
    ease: "elastic",
  });
};

let loader = null;
export const loading = function (node) {
  loader = gsap.to(node, {
    opacity: 1,
    width: "100%",
    duration: 10,
  });
};

export const stopLoading = function (node) {
  if (loader) loader.kill();
  gsap.to(node, {
    opacity: 0,
    width: "0%",
    duration: 0.5,
  });
};

export const fadeOut = function (node) {
  gsap.to(node, {
    opacity: 0,
    scale: 0.5,
    duration: 0.5,
    ease: "bounce",
  });
};
