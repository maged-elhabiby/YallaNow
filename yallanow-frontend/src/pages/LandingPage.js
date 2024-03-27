import React from 'react';

const LandingPage = () => {
  const heroBackgroundImageUrl = "https://storage.googleapis.com/tmp-bucket-json-data/1.jpg";
  const testimonialsBackgroundImageUrl = "https://storage.googleapis.com/tmp-bucket-json-data/2.jpg";

  return (
    <div className="flex flex-col min-h-screen overflow-hidden">
      {/* Hero section */}
      <section className="relative" style={{ backgroundImage: `url(${heroBackgroundImageUrl})`, backgroundSize: 'cover', backgroundPosition: 'center' }}>
        <div className="max-w-6xl mx-auto px-4 sm:px-6">
          <div className="pt-32 pb-12 mb-20 md:pt-40 md:pb-20 text-center text-white">
            <h1 className="text-5xl md:text-6xl font-bold mb-4">Welcome to Yalla Now</h1>
            <p className="text-xl">Connect, plan, and celebrate events effortlessly with friends and family.</p>
            <div className="mt-8">
              <a href="/signup" className="bg-pink-600 text-white rounded-full py-3 px-6">Get Started</a>
            </div>
          </div>
        </div>
      </section>

      {/* Features section */}
      <section className="border-t border-gray-200">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 py-12">
          <h2 className="text-3xl font-bold text-center">Our Features</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
            <div className="text-center">
              <h3 className="text-xl font-semibold mb-2">Finding Events</h3>
              <p className="text-gray-600">Organize your events with a dedicated space for planning, making collaboration easy and fun.</p>
            </div>
            <div className="text-center">
              <h3 className="text-xl font-semibold mb-2">Finding Communities</h3>
              <p className="text-gray-600"></p>
            </div>
            <div className="text-center">
              <h3 className="text-xl font-semibold mb-2">RSVP</h3>
              <p className="text-gray-600"></p>
            </div>
          </div>
        </div>
      </section>

      {/* Testimonials section */}
      <section className="bg-gray-100" style={{ backgroundImage: `url(${testimonialsBackgroundImageUrl})`, backgroundSize: 'cover', backgroundPosition: 'center', backgroundBlendMode: 'multiply', backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
        <div className="max-w-6xl mx-auto px-4 sm:px-6 py-12 text-white">
          <h2 className="text-3xl font-bold text-center">What Our Users Say</h2>
          <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-8">
            {/* Add testimonials here */}
          </div>
        </div>
      </section>

      {/* Call to action section */}
      <section className="bg-pink-600">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 py-12 text-center">
          <h2 className="text-3xl font-bold text-white mb-4">Ready to Get Started?</h2>
          <p className="text-white text-lg">Join us today and start exploring the endless possibilities.</p>
          <div className="mt-8">
            <a href="/signup" className="bg-white text-pink-600 rounded-full py-3 px-6">Sign Up Now</a>
          </div>
        </div>
      </section>
    </div>
  );
};

export default LandingPage;
