function logout() {
    deleteOldCaches("delete");
    deleteCookie("token");
    window.location.href = "/login";

}

function renderNavbar(navbar) {
    let html = "";
    for (let i = 0; i < navbar.length; i++) {
        html += "<li class=\"nav-item\">\n" + "<a class=\"nav-link\" href=\"" + navbar[i].url + "\">"
            + navbar[i].text + "</a>\n</li>";
    }
    html += "<li class=\"nav-item\">\n<button class=\"button__logout\" onclick=\"logout()\">Выйти</button>\n</li>";
    html += "<li class=\"nav-item\">\n<button class=\"button__bugReports\" data-bs-toggle=\"modal\" data-bs-target=\"#exampleModal\">сообщить об ошибке</button>\n</li>";

    document.getElementById("navbar").innerHTML = html;
}

// Try to get data from the cache, but fall back to fetching it live.
async function getData() {
    let cacheName = "navbar";
    const url = '/navbar';
    let cachedData = await getCachedData(cacheName, url);

    if (cachedData) {
        console.log('Retrieved cached data');
        renderNavbar(cachedData);
        return cachedData;
    }

    console.log('Fetching fresh data');

    const cacheStorage = await caches.open(cacheName);
    await cacheStorage.add(url);
    cachedData = await getCachedData(cacheName, url);
    await deleteOldCaches(cacheName);
    renderNavbar(cachedData);

    return cachedData;
}

// Get data from the cache.
async function getCachedData(cacheName, url) {
    const cacheStorage = await caches.open(cacheName);
    const cachedResponse = await cacheStorage.match(url);

    if (!cachedResponse || !cachedResponse.ok) {
        return false;
    }

    return await cachedResponse.json();
}

// Delete any old caches to respect user's disk space.
async function deleteOldCaches(currentCache) {
    const keys = await caches.keys();

    for (const key of keys) {
        const isOurCache = 'navbar' === key.substring(0, 6);

        if (currentCache === key || !isOurCache) {
            continue;
        }

        caches.delete(key);
    }
}

try {
    const data = getData();
    console.log({data});
} catch (error) {
    console.error({error});
}
