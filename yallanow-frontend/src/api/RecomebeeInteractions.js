import recombee from 'recombee-js-api-client';

// Initialize the Recombee client with API credentials and region setting
const client = new recombee.ApiClient('yallanow-dev', 'tihd0AD7P0JckHLvVmV0YBW6l4RvKrCsTAaFTZ32DyCD86rSM26oYe5gpW5tJF2O', { region: 'ca-east' });

const RecombeeInteractions = {

  // Adds a detail view interaction for a user and an item in Recombee. Optionally includes a recommendation ID.
  addDetailViewInteraction: (userId, itemId, recommId) => {
    // Convert to strings
    userId = String(userId);
    itemId = String(itemId);
    const opt = recommId == null ? {} : { recommId: String(recommId) };

    client.send(new recombee.AddDetailView(userId, itemId, opt));
  },

  // Adds a purchase interaction for a user and an item in Recombee. Optionally includes a recommendation ID.
  addPurchaseInteraction: (userId, itemId, recommId) => {
    // Convert to strings
    userId = String(userId);
    itemId = String(itemId);
    const opt = recommId == null ? {} : { recommId: String(recommId) };

    client.send(new recombee.AddPurchase(userId, itemId, opt));
  },
};


export default RecombeeInteractions;
