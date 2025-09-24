// Simple Service Worker for CityBus Tracker
const CACHE_NAME = 'citybus-v1';
const urlsToCache = [
  '/',
  '/styles.css',
  '/script.js',
  '/animations.js'
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then((cache) => cache.addAll(urlsToCache))
  );
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request)
      .then((response) => {
        return response || fetch(event.request);
      })
  );
});