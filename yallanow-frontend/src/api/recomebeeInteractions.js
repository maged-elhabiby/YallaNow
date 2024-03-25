import recombee from 'recombee-js-api-client';

const client = new recombee.ApiClient('yallanow-dev', 'tihd0AD7P0JckHLvVmV0YBW6l4RvKrCsTAaFTZ32DyCD86rSM26oYe5gpW5tJF2O', { region: 'ca-east' });

const recombeeInteractions = {
  addDetailViewInteraction: (userId, itemId, recommId) => {
    client.send(new recombee.AddDetailView(userId, itemId, { recommId }));
  },

  addPurchaseInteraction: (userId, itemId, recommId) => {
    client.send(new recombee.AddPurchase(userId, itemId, { recommId }));
  },
};

export default recombeeInteractions;
