(function () {
  var storageKey = "swagger-ui-theme";
  var root = document.documentElement;
  var button;

  function savedTheme() {
    try {
      return localStorage.getItem(storageKey);
    } catch (error) {
      return null;
    }
  }

  function saveTheme(theme) {
    try {
      localStorage.setItem(storageKey, theme);
    } catch (error) {
      return;
    }
  }

  function preferredTheme() {
    var theme = savedTheme();
    if (theme === "dark" || theme === "light") {
      return theme;
    }

    if (window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches) {
      return "dark";
    }

    return "light";
  }

  function setTheme(theme) {
    root.setAttribute("data-swagger-theme", theme);
    saveTheme(theme);

    if (button) {
      button.setAttribute("aria-pressed", String(theme === "dark"));
      button.textContent = theme === "dark" ? "Light mode" : "Dark mode";
    }
  }

  function createToggle() {
    var wrapper = document.querySelector(".swagger-ui .topbar .wrapper");
    if (!wrapper || document.querySelector(".swagger-theme-toggle")) {
      return Boolean(document.querySelector(".swagger-theme-toggle"));
    }

    button = document.createElement("button");
    button.type = "button";
    button.className = "swagger-theme-toggle";
    button.addEventListener("click", function () {
      setTheme(root.getAttribute("data-swagger-theme") === "dark" ? "light" : "dark");
    });

    wrapper.appendChild(button);
    setTheme(root.getAttribute("data-swagger-theme") || preferredTheme());
    return true;
  }

  function createFloatingToggle() {
    if (document.querySelector(".swagger-theme-toggle")) {
      return true;
    }

    button = document.createElement("button");
    button.type = "button";
    button.className = "swagger-theme-toggle swagger-theme-toggle--floating";
    button.addEventListener("click", function () {
      setTheme(root.getAttribute("data-swagger-theme") === "dark" ? "light" : "dark");
    });

    document.body.appendChild(button);
    setTheme(root.getAttribute("data-swagger-theme") || preferredTheme());
    return true;
  }

  setTheme(preferredTheme());

  if (!createToggle()) {
    var observer = new MutationObserver(function () {
      if (createToggle()) {
        observer.disconnect();
      }
    });

    observer.observe(document.body, { childList: true, subtree: true });

    window.setTimeout(function () {
      createFloatingToggle();
    }, 500);
  }
})();
