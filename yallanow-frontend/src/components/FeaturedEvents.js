import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

const FeaturedEventsCarousel = ({ featuredEvents }) => {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 my-10">
      <Swiper
        spaceBetween={30}
        slidesPerView={1}
        onSlideChange={() => console.log('slide change')}
        onSwiper={(swiper) => console.log(swiper)}
      >
        {featuredEvents.map((event, index) => (
          <SwiperSlide key={index}>
            <div className="h-96 bg-cover bg-center rounded-lg" style={{ backgroundImage: `url(${event.eventImageUrl})` }}>
              <div className="h-full bg-black bg-opacity-30 flex flex-col justify-end p-6">
                <h3 className="text-2xl font-bold text-white">{event.eventTitle}</h3>
                <p className="text-lg text-white">{event.eventDescription}</p>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default FeaturedEventsCarousel;
